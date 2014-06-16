package com.onlinever.usercenter.handler.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.onlinever.commons.exception.OnlineverException;
import com.onlinever.commons.util.NormalReturn;
import com.onlinever.commons.util.Utilities;
import com.onlinever.usercenter.handler.IGatewayHandler;
import com.onlinever.usercenter.model.User;
import com.onlinever.usercenter.service.IUserService;


@Service("gatewayHandler")
public class GatewayHandler implements IGatewayHandler{
	
	private static Logger log = Logger.getLogger(GatewayHandler.class);

	@Autowired
	private IUserService userService;
	
	public NormalReturn getSession(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		try{
			nr.setResult(request.getSession().getAttribute("user"));
		}catch(OnlineverException e){
			nr.setStatusCode(e.errorCode);
			nr.setMsg(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			nr.setMsg(e.getMessage());
			nr.setStatusCode(OnlineverException.UNKNOWN_ERROR);
		}
		return nr;
	}
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn register(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		try{
//			JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
//			User user = JSONObject.toJavaObject(json, User.class);
//			//注册IP
//			if(null == user.getRegisterIp() || !user.getRegisterIp().equals("")){
//				user.setRegisterIp(Utilities.getRemortIP(request));
//			}
//			//注册时间
//			user.setRegisterTime(new Date());
			log.info("register");
			request.getSession().setAttribute("user", "945688");
//			userService.addUser(user);
		}catch(OnlineverException e){
			nr.setStatusCode(e.errorCode);
			nr.setMsg(e.getMessage());
		}catch(Exception e){
			log.error(this,e);
			nr.setMsg(e.getMessage());
			nr.setStatusCode(OnlineverException.UNKNOWN_ERROR);
		}
		return nr;
	}
}
