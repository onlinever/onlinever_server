package com.onlinever.usercenter.handler.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.onlinever.commons.util.NormalReturn;
import com.onlinever.usercenter.handler.IGatewayHandler;

@Service("gatewayHandler")
public class GatewayHandler implements IGatewayHandler{
	
	private static Logger log = Logger.getLogger(GatewayHandler.class);

	@Override
	public NormalReturn saveApplication(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("saveApplication......");
		return null;
	}
}
