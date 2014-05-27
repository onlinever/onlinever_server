package com.onlinever.usercenter.handler.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinever.commons.exception.OnlineverException;
import com.onlinever.commons.util.NormalReturn;
import com.onlinever.commons.util.Utilities;
import com.onlinever.usercenter.handler.IGatewayHandler;
import com.onlinever.usercenter.model.City;
import com.onlinever.usercenter.model.Cityregion;
import com.onlinever.usercenter.model.Province;
import com.onlinever.usercenter.service.IUserService;


@Service("gatewayHandler")
public class GatewayHandler implements IGatewayHandler{
	
	private static Logger log = Logger.getLogger(GatewayHandler.class);

	@Resource
	private VelocityEngine velocityEngine;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public NormalReturn saveApplication(HttpServletRequest request,
			HttpServletResponse response) {
		NormalReturn nr = new NormalReturn();
		log.info(request.getAttribute(Utilities.INPUT_JSON_KEY));
		try{
			List<Province> provinces  = userService.getAllProvince();
			for (Province province : provinces) {
				List<City> citys = userService.getCityByProvinceId(province.getId());
				province.setCitys(citys);
				for (City city : citys) {
					List<Cityregion> cityregions = userService.getCityregionByCityId(city.getId());
					city.setCityregions(cityregions);
				}
			}
			nr.setResult(provinces);
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
}
