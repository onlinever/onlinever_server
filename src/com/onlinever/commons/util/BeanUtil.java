package com.onlinever.commons.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BeanUtil {
	public BeanUtil(){
		
	}
	public static Object getBean(HttpServletRequest request, String beanName){
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		return context.getBean(beanName);
	}
}
