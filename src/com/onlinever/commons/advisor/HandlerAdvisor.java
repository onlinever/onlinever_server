package com.onlinever.commons.advisor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.onlinever.commons.exception.OnlineverException;
import com.onlinever.commons.util.NormalReturn;
import com.onlinever.commons.util.Utilities;

@Aspect
@Service
public class HandlerAdvisor{
	private final static Logger log = Logger.getLogger(HandlerAdvisor.class);
	
	@Pointcut("execution(* com.onlinever.usercenter.handler.impl.GatewayHandler.save*(..))"
			+ " || execution(* com.onlinever.usercenter.handler.impl.GatewayHandler.*(..))"
			)
	private void doAction(){}
	
	@Around("doAction()")
	public Object log(ProceedingJoinPoint joinPoint){
		Object resp = null;
		try{
			HttpServletRequest request = getRequest(joinPoint);
			StringBuilder sb = new StringBuilder(500);
			sb.append(request.getMethod())
			  .append("[")
			  .append(Utilities.getRemortIP(request))
			  .append("|")
			  .append(request.getCharacterEncoding())
			  .append("]")
			  .append(request.getRequestURI())
			  .append(getJSONObject(request));
			log.info(sb.toString());
			try {
				resp = joinPoint.proceed();
			} catch (Throwable e) {
				NormalReturn nr = new NormalReturn();
				nr.setMsg(e.getMessage());
				nr.setStatusCode(OnlineverException.UNKNOWN_ERROR);
				resp = nr;
			}
			log.info(ReflectionToStringBuilder.reflectionToString(resp, ToStringStyle.SHORT_PREFIX_STYLE));
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
				
		return resp;
	}
	
	protected JSONObject getJSONObject(HttpServletRequest request) throws OnlineverException, IOException{
		JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
		if(json == null){
			json = new JSONObject();
		}
		return json;
	}
	
	
	protected HttpServletRequest getRequest(ProceedingJoinPoint joinPoint){
		HttpServletRequest request = null;
		Object[] args = joinPoint.getArgs();
		if(args != null && args.length > 0){
			if(args[0] instanceof HttpServletRequest){
				request = (HttpServletRequest) args[0];
			}
		}
		return request;
	}
}
