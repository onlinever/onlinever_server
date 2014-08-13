package com.onlinever.commons.cache;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.onlinever.commons.ExecutorFactory;
import com.onlinever.commons.util.CostTime;
import com.onlinever.commons.util.Utilities;

/**
 * 
 * @author Demon
 *	如果参数是Map 取Map中的memKey作为key ，如果Map中没有memKey则取id作为key
 *	如果参数是基础类型java.lang.* 、数组、原始类型或基础类型: 所有参数相加作为key
 *  如果参数是实体类，取实体类中的PK,如果没有取实体类中id作为key
 *  缓存默认10分钟刷新,可以config.properties 设置 memcachedExpiryTime
 *  
 *  在需要缓存的方法加注解：@MemCaching
 *  在需要清除缓存的方法加注解：@MemFlush
 *  
 *  配置文件加：
isUsedMemcached=true
tt.cache.server = newmall.mem.tg.local:11211
memcachedPre = api_review_
#memcached expiry time ms
memcachedExpiryTime=600000

 *  @version 0.1 入缓存走异步
 *  @lastmodify 2014-09-28
 */

@Aspect
@Repository
public class CacheManager {
	private static Logger log = Logger.getLogger(CacheManager.class);
	
	@Autowired
	private MemcacheDao memcacheDao;
	
	@Value("#{config['use.memcache.formodel']}")
	private boolean isUsedMemcached;

	@Pointcut("@annotation (com.onlinever.commons.cache.MemCaching)")
    private void useCache() {}
	
	@Pointcut("@annotation (com.onlinever.commons.cache.MemFlush)")
	private void flushCache(){}
	
	@AfterReturning("flushCache()")
	public void flushMemcache(final JoinPoint joinPoint) throws Throwable{
		Callable<Boolean> cmd = new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception {
				MethodSignature signature = (MethodSignature) joinPoint.getSignature();
				Method method = signature.getMethod();
				
				Object[] args = joinPoint.getArgs();
				if(args != null && args.length > 0){
					Object obj = getEntityClass(args);
					String modelKey = getModelKey(obj);
			    	if(modelKey == null){
			    		log.warn(String.format("方法%s运行时没有取到缓存key！", method.getName()));
			    	}else{
				    	String memKey = getCacheKey(obj.getClass(), modelKey);
				    	memcacheDao.delete(memKey);
			    	}
				}
				return true;
			}
		};
		ExecutorFactory.fixedExecutor.submit(cmd);
	}
    
	@SuppressWarnings("unchecked")
	@Around("useCache()")
    public Object useMemcache(ProceedingJoinPoint joinPoint) throws Throwable{
    	Object[] args = joinPoint.getArgs();
    	MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	Method method = signature.getMethod();
    	String methodName = method.getName();

    	if(!isUsedMemcached){//不需要走缓存
    		return joinPoint.proceed();
    	}
		CostTime cost = new CostTime();
		cost.start();
		String modelKey = getModelKey(args);
    	if(modelKey == null){
    		log.warn(String.format("方法%s运行时没有取到缓存key！", methodName));
    		return joinPoint.proceed();
    	}
    	
    	//取缓存
    	String memKey = getCacheKey(signature.getReturnType(), modelKey);
		Object value = memcacheDao.get(memKey);
		if(value != null){
			value = JSONObject.parseObject(value.toString(), signature.getReturnType());
			log.info(String.format("get data from cache:%s costTime:%d", memKey, cost.cost()));
			return value;
		}else{
			Object resp = joinPoint.proceed();
			int timeout = 360*24*3600*1000;
			asycSetCache(memKey, resp, timeout);
			return resp;
		}
    }

	private String getModelKey(Object obj){
		String modelKey = null;
		if(obj instanceof Integer || obj instanceof String){
			return String.valueOf(obj);
		}
		
		//取实体类中带PK注解的属性作为memkey
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(PK.class)){
				try {
					field.setAccessible(true);
					Object value = field.get(obj);
					modelKey = String.valueOf(value);
					break;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		if(modelKey == null){
			try{
				Method m = obj.getClass().getMethod("getId");
				modelKey = String.valueOf(m.invoke(obj));//取属性名为id作为key
			}catch(Exception e){
				log.warn(String.format("%s中没有id属性", obj.getClass().getName()));
			}
		}
		return modelKey;
	}
	
	private String getModelKey(Object[] args)
			throws IllegalAccessException, InvocationTargetException {
		String modelKey = null;
		for (Object obj :args){
    		if(obj instanceof Map){//如果参数是Map，取Map中的id作为key
    			if(((Map<?, ?>)obj).containsKey("memKey")){//取Map中的memKey作为key
    				modelKey = (String) ((Map<?, ?>)obj).get("memKey");
    			}
    			if(modelKey == null && ((Map<?, ?>)obj).containsKey("id")){//如果没有设置，取Map中的id作为key
    				modelKey = (String) ((Map<?, ?>)obj).get("id");
    			}
    			break;
    		}
    	}
		if(modelKey == null){//参数不是map
			Class<?> entryclass = getEntityClass(args).getClass();
			if(entryclass != null){
				if(entryclass.isArray() || entryclass.isPrimitive() || entryclass.toString().indexOf("java.lang") > 0 || ClassUtils.isAssignable(entryclass, Map.class)){//数组、原始类型或基础类型
					modelKey = "";
					for(Object obj : args){
						if(obj == null)
							continue;
						else if(obj.getClass().isArray())
							obj = ArrayUtils.toString(obj);
						modelKey += String.valueOf(obj);//所有参数相加做为memKey
					}
				}else if(entryclass.toString().indexOf("java.util") > 0){
					log.warn("不支持java.util类型除Map以外的参数");
				}else{
					//如果参数是实体类，取实体类中的PK
		    		modelKey = getModelKey(args[0]);
				}		
			}
		}
		return modelKey;
	}

	private Object getEntityClass(Object[] args) {
		Object obj = null;
		for(Object arg : args){
			if(arg == null)
				continue;
			obj = arg;//找到第一个非空的参数
			break;
		}
		return obj;
	}

	/**
	 * 异步存入缓存
	 * @param key
	 * @param obj
	 * @param timeout
	 * @return
	 */
	public boolean asycSetCache(final String key,final Object obj,final int timeout) {
		Callable<Boolean> cmd = new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception {
				return memcacheDao.set(key, timeout, JSONObject.toJSONString(obj, SerializerFeature.BrowserCompatible));
			}
		};
		ExecutorFactory.fixedExecutor.submit(cmd);
		return true;
	}
	
	/**
	 * 异步删除缓存
	 * @param key
	 * @return
	 */
	public boolean asycDelCache(final String key) {
		Callable<Boolean> cmd = new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception {
				return memcacheDao.delete(key);
			}
		};
		ExecutorFactory.fixedExecutor.submit(cmd);
		return true;
	}
	
    private String getCacheKey(Class<?> clazz, String arg){
    	StringBuilder sb = new StringBuilder();
    	sb.append(MemcacheDao.KEY_PREX)
    	  .append(ClassUtils.getShortClassName(clazz))
    	  .append("_").append(arg.length() > 40 ? Utilities.encodePassword(arg, "md5"): arg);
    	return sb.toString();
    }
} 
