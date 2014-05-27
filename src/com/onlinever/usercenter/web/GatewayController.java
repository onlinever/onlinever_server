package com.onlinever.usercenter.web;

import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onlinever.commons.util.CostTime;
import com.onlinever.commons.util.NormalReturn;
import com.onlinever.commons.util.Utilities;
import com.onlinever.usercenter.handler.IGatewayHandler;

@Controller
@RequestMapping("/gateway")
public class GatewayController{
	@Autowired
	private IGatewayHandler gatewayHandler;
	
	@RequestMapping(value="/*")
	@ResponseBody
	public NormalReturn doAction(HttpServletRequest request, HttpServletResponse response){
		NormalReturn nr = null;
		CostTime costTime = new CostTime();
		
		try {
			String[] urls = request.getRequestURI().split("/");
			String methodName = urls[urls.length - 1];
		
			methodName = methodName.split("\\.")[0];

			JSONObject json = (JSONObject)request.getAttribute(Utilities.INPUT_JSON_KEY);
			
			if(json == null){
				String content = FileCopyUtils.copyToString(new InputStreamReader(request.getInputStream(), Charset.forName("UTF-8")));
				if(content != null && !"".equals(content)){
					json = JSONObject.parseObject(content);
					if(json != null){
						request.setAttribute(Utilities.INPUT_JSON_KEY, json);
					}
				}
			}
			Method method = gatewayHandler.getClass().getMethod(methodName
					, HttpServletRequest.class, HttpServletResponse.class);
			nr = (NormalReturn)method.invoke(gatewayHandler, request, response);
			nr.setCostTime(costTime.cost());
		} catch (Exception e) {
			nr = new NormalReturn();
			nr.setMsg(e.getMessage());
			nr.setCostTime(costTime.cost());
		}
		return nr;
	}
}
