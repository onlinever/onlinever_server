package com.onlinever.usercenter.dao;

import com.onlinever.usercenter.model.User;

public interface UserMapper {
	 
	/**
	 * 添加用户
	 * @param record
	 * @return 
	 */
    int insert(User record);
    
    /**
     * 根据id查找用户
     * @param id
     * @return User
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 根据id修改用户信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据id修改用户
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);
    
    /**
     * 用户是否存在
     * @param record
     * @return
     */
    int getUserIsExists(User record);
}