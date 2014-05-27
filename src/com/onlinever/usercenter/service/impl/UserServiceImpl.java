package com.onlinever.usercenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinever.commons.datasource.UseSlave;
import com.onlinever.usercenter.dao.CityMapper;
import com.onlinever.usercenter.dao.CityregionMapper;
import com.onlinever.usercenter.dao.ProvinceMapper;
import com.onlinever.usercenter.dao.UserMapper;
import com.onlinever.usercenter.model.City;
import com.onlinever.usercenter.model.Cityregion;
import com.onlinever.usercenter.model.Province;
import com.onlinever.usercenter.service.IUserService;


@Repository("UserService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CityMapper cityMapper;
	
	@Autowired
	private CityregionMapper cityregionMapper;
	
	@Autowired 
	private ProvinceMapper provinceMapper;
	
	@UseSlave
	public Province getProvince(Integer id){
		return provinceMapper.selectByPrimaryKey(id);
	}
	
	@UseSlave
	public List<Province> getAllProvince(){
		return provinceMapper.getAllProvince();
	}
	
	@UseSlave
	public List<City> getCityByProvinceId(Integer id){
		return cityMapper.getCityByProvinceId(id);
	}
	
	@UseSlave
	public List<Cityregion> getCityregionByCityId(Integer id){
		return cityregionMapper.getCityregionsByCityId(id);
	}
	
	
}
