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
import com.onlinever.commons.util.ResourceUtils;
import com.onlinever.commons.util.Utilities;
import com.onlinever.usercenter.handler.IGatewayHandler;
import com.onlinever.usercenter.model.User;
import com.onlinever.usercenter.service.IUserService;
import com.onlinever.usercenter.task.SendEmailTask;


@Service("gatewayHandler")
public class GatewayHandler implements IGatewayHandler{
	
	private static Logger log = Logger.getLogger(GatewayHandler.class);

	@Autowired
	private IUserService userService;
	
	/**
	 * 获取session
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn getSession(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		try{
			JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
			String key = json.getString("key");
			if(key==null){
				throw new OnlineverException(OnlineverException.INPUT_PARAM_INVALID);
			}
			Object o = new Object();
			o = request.getSession().getAttribute(key);
			if(o == null){
				throw new OnlineverException(OnlineverException.NOT_EXIST);
			}
			nr.setResult(o);
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
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		try{
			request.getSession().removeAttribute(Utilities.USER_SESSION_KEY);
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
	/**
	 * 发送验证邮件
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn sendVerifyMail(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		try{
			JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
			String email = json.getString("email");
			if(email==null){
				throw new OnlineverException(OnlineverException.INPUT_PARAM_INVALID);
			}
			Object key = request.getSession().getAttribute("emailVerifyCode");
			if(key == null){
				key = Utilities.getRandomPwd(6);
				request.getSession().setAttribute("emailVerifyCode",key);
			}
			//sendEmail
			Utilities.getExecutor().execute(new SendEmailTask(email, ResourceUtils.getString("mail.register.subject"), ResourceUtils.getString("mail.register.content",email,key)));
			nr.setResult("success");
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
	/**
	 * 邮件验证
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn verifyMail(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		try{
			JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
			String email = json.getString("email");
			String emailVerifyCode = json.getString("emailVerifyCode");
			if(email == null || emailVerifyCode == null){
				throw new OnlineverException(OnlineverException.INPUT_PARAM_INVALID);
			}
			Object key = request.getSession().getAttribute("emailVerifyCode");
			if(!key.equals(emailVerifyCode)){
				throw new OnlineverException(OnlineverException.EMAIL_VALIDATION_FAIL);
			}
			nr.setResult("success");
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
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn register(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		User user = new User();
		try{
			JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
			user = JSONObject.toJavaObject(json, User.class);
			//注册IP
			if(null == user.getRegisterIp() || !user.getRegisterIp().equals("")){
				user.setRegisterIp(Utilities.getRemortIP(request));
			}
			//注册时间
			user.setRegisterTime(new Date());
			log.info(user.getLoginName());
			request.getSession().removeAttribute(Utilities.USER_SESSION_KEY);
			request.getSession().setAttribute(Utilities.USER_SESSION_KEY, user.getLoginName());
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
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 */
	public NormalReturn login(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		User user = new User();
		try{
			JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
			user = JSONObject.toJavaObject(json, User.class);
			if(user.getUserName()==null || user.getPassword() ==null){
				throw new OnlineverException(OnlineverException.REQUIRED_PARAMETER_MISSING);
			}
			//最后登录IP
			user.setLastLoginIp(Utilities.getRemortIP(request));
			User u = userService.doLogin(user);
			request.getSession().removeAttribute(Utilities.USER_SESSION_KEY);
			request.getSession().setAttribute(Utilities.USER_SESSION_KEY, u);
			nr.setResult(u.getLoginName());
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
