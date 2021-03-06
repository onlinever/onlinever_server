package com.onlinever.usercenter.service;

import com.onlinever.commons.util.AreaList;
import com.onlinever.usercenter.model.Province;
import com.onlinever.usercenter.model.User;

public interface IUserService {
	/**
	 * 取得省份信息
	 * @param id
	 * @return
	 */
	public Province getProvince(Integer id);
	
	/**
	 * 获取所有省份
	 * @return
	 */
	public AreaList getAllProvince(Integer memid);
	
	/**
	 * 获取省份下的城市
	 * @return
	 */
	public AreaList getCityByProvinceId(Integer id);
	
	/**
	 * 获取城市下的所有地区
	 * @param id
	 * @return
	 */
	public AreaList getCityregionByCityId(Integer id);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * 用户是否已存在
	 * @param user
	 * @return
	 */
	public int getUserIsExists(User user);
	
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User doLogin(User user);
}
