package com.onlinever.usercenter.dao;

import com.onlinever.usercenter.model.Reference;

public interface ReferenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reference record);

    int insertSelective(Reference record);

    Reference selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reference record);

    int updateByPrimaryKey(Reference record);
}