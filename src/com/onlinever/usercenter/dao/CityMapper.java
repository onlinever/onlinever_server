package com.onlinever.usercenter.dao;

import com.onlinever.commons.util.AreaList;
import com.onlinever.usercenter.model.City;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
    /**
     * 获取省份下城市列表
     * @param provinceId
     * @return
     */
    AreaList getCityByProvinceId(Integer provinceId);
}