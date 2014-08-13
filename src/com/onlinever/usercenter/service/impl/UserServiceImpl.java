package com.onlinever.usercenter.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinever.commons.cache.MemCaching;
import com.onlinever.commons.datasource.UseSlave;
import com.onlinever.commons.exception.OnlineverException;
import com.onlinever.commons.util.AreaList;
import com.onlinever.commons.util.Utilities;
import com.onlinever.usercenter.dao.CityMapper;
import com.onlinever.usercenter.dao.CityregionMapper;
import com.onlinever.usercenter.dao.ProvinceMapper;
import com.onlinever.usercenter.dao.UserMapper;
import com.onlinever.usercenter.model.Province;
import com.onlinever.usercenter.model.User;
import com.onlinever.usercenter.service.IUserService;


@Repository("userService")
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
	
	@MemCaching
	@UseSlave
	@Override
	public Province getProvince(Integer id){
		return provinceMapper.selectByPrimaryKey(id);
	}
	@MemCaching
	@UseSlave
	@Override
	public AreaList getAllProvince(Integer memid){
		return provinceMapper.getAllProvince();
	}
	
	@MemCaching
	@UseSlave
	@Override
	public AreaList getCityByProvinceId(Integer id){
		return cityMapper.getCityByProvinceId(id);
	}
	
	@MemCaching
	@UseSlave
	public AreaList getCityregionByCityId(Integer id){
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
		if(this.getUserIsExists(user) > 0){
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
	public int getUserIsExists(User user){
		if(user.getLoginName() != null || user.getEmail() != null || user.getMobile() != null){
			return userMapper.getUserIsExists(user);
		}
		return 0;
	}
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User doLogin(User user) {
		//获取登录类型
		user = Utilities.getUserParamType(user);
		//取得用户Id
		int userId = getUserIsExists(user);
		//用户是否存在
		if(userId == 0){
			throw new OnlineverException(OnlineverException.USER_DOES_NOT_EXIST);
		}
		user.setId(userId);
		//验证密码
		User u = userMapper.getUserByPword(user);
		if(u == null){
			throw new OnlineverException(OnlineverException.PASSWORD_VALIDATION_FAIL);
		}
		//登录成功 修改最后登录记录
		u.setLastLoginIp(user.getLastLoginIp());
		u.setLastLoginTime(new Date());
		userMapper.updateByPrimaryKey(u);
		return u;
	}
	
}
