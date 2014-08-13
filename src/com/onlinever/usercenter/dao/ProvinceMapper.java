package com.onlinever.usercenter.dao;

import com.onlinever.commons.util.AreaList;
import com.onlinever.usercenter.model.Province;

public interface ProvinceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Province record);

    int insertSelective(Province record);

    Province selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);
    
    /**
     * 所有省份列表
     * @return
     */
    AreaList getAllProvince();
}