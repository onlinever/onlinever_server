package com.onlinever.usercenter.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinever.commons.util.NormalReturn;

public interface IGatewayHandler {
	/**
	 * 获取session
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn getSession(HttpServletRequest request,HttpServletResponse response);
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn register(HttpServletRequest request,HttpServletResponse response);
}
