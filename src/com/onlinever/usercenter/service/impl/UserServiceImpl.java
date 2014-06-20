package com.onlinever.usercenter.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinever.commons.datasource.UseSlave;
import com.onlinever.commons.exception.OnlineverException;
import com.onlinever.commons.util.Utilities;
import com.onlinever.usercenter.dao.CityMapper;
import com.onlinever.usercenter.dao.CityregionMapper;
import com.onlinever.usercenter.dao.ProvinceMapper;
import com.onlinever.usercenter.dao.UserMapper;
import com.onlinever.usercenter.model.City;
import com.onlinever.usercenter.model.Cityregion;
import com.onlinever.usercenter.model.Province;
import com.onlinever.usercenter.model.User;
import com.onlinever.usercenter.service.IUserService;


@Repository("UserService")
public class UserServiceImpl implements IUserService {
	private static Logger log = Logger.getLogger(UserServiceImpl.class);
	
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
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addUser(User user){
		if(user.getLoginName()==null){
			if(user.getEmail() != null){		//email不为空，默认为email
				user.setLoginName(user.getEmail());
			}else if(user.getMobile() != null){	//mobile不为空，生成乱码帐号
				user.setLoginName(Utilities.getRandomUname(user.getMobile()));
			}else {
				log.error("insert user fail! loginName can not be null");
				throw new OnlineverException(OnlineverException.INPUT_PARAM_INVALID);
			}
		}
		//用户唯一验证
		if(this.getUserIsExists(user)){
			throw new OnlineverException(OnlineverException.USER_ALREADY_EXIST);
		}
		//密码不能为空
		if(user.getPassword()==null){
			throw new OnlineverException(OnlineverException.INPUT_PARAM_INVALID);
		}
		return 0;
	}
	/**
	 * 用户是否已存在
	 * @param user
	 * @return
	 */
	public boolean getUserIsExists(User user){
		if(user.getLoginName() != null || user.getEmail() != null || user.getMobile() != null){
			return userMapper.getUserIsExists(user) > 0 ? true : false;
		}
		return true;
	}
	
}
