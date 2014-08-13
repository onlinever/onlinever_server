package com.onlinever.usercenter.dao;

import com.onlinever.commons.util.AreaList;
import com.onlinever.usercenter.model.Cityregion;

public interface CityregionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cityregion record);

    int insertSelective(Cityregion record);

    Cityregion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cityregion record);

    int updateByPrimaryKey(Cityregion record);
    
    AreaList getCityregionsByCityId(Integer cityId);
}