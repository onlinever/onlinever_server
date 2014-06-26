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
	 * 发送验证邮件
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn sendVerifyMail(HttpServletRequest request,HttpServletResponse response);
	/**
	 * 邮件验证
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn verifyMail(HttpServletRequest request,HttpServletResponse response);
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn register(HttpServletRequest request,HttpServletResponse response);
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn login(HttpServletRequest request,HttpServletResponse response);
}
