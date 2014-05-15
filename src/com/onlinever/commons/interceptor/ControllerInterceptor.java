package com.onlinever.commons.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.onlinever.commons.util.Utilities;

public class ControllerInterceptor extends HandlerInterceptorAdapter {
	
	private final static Logger log = Logger.getLogger(ControllerInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception {
		try{
			StringBuilder sb = new StringBuilder(1000);
			sb.append(request.getMethod())
			  .append("[")
			  .append(Utilities.getRemortIP(request))
			  .append("|")
			  .append(request.getCharacterEncoding())
			  .append("]")
			  .append(request.getRequestURL());
			 if(request.getQueryString() != null){
				 sb.append("?");
				 sb.append(request.getQueryString());
			 }else{
				 if(request.getParameterMap() != null){
					 sb.append("-");
					 sb.append(ReflectionToStringBuilder.reflectionToString(request.getParameterMap().entrySet(),
				              ToStringStyle.SHORT_PREFIX_STYLE));
				 }
			 }
			  
		 	log.info(sb.toString());
		}catch(Throwable e){
		}
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		if(modelAndView != null){
			log.info(modelAndView.toString());
		}
	}
}
