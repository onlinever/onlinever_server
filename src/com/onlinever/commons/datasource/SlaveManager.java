package com.onlinever.commons.datasource;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Repository;


@Aspect
@Repository
public class SlaveManager {
	
	@Pointcut("@annotation (com.onlinever.commons.datasource.UseSlave)")
	private void useSlave(){}
	
	@Pointcut("@annotation (com.onlinever.commons.datasource.UseMaster)")
	private void useMaster(){}
	
	@Around("useMaster()")
	public Object useMaster(ProceedingJoinPoint joinPoint) throws Throwable{
		DataSourceContextHolder.setDataSourceKey("master");
		Object resp = joinPoint.proceed();
		DataSourceContextHolder.clearDataSourceKey();
		return resp;
	}
	
	@Around("useSlave()")
	public Object useSlave(ProceedingJoinPoint joinPoint) throws Throwable{
		String key = DataSourceContextHolder.getDataSourceKey();
		if(!"master".equals(key)){
			DataSourceContextHolder.setDataSourceKey("slave");
		}
		Object resp = joinPoint.proceed();
		DataSourceContextHolder.clearDataSourceKey();
		return resp;
	}
}
