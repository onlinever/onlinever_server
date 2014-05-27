package com.onlinever.usercenter.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinever.commons.util.NormalReturn;

public interface IGatewayHandler {
	
	public NormalReturn saveApplication(HttpServletRequest request, HttpServletResponse response);
}
