package com.onlinever.usercenter.model;

import java.util.Date;
import java.util.List;

import com.onlinever.commons.cache.PK;

public class Province {
	
	@PK
    private Integer id;

    private String name;

    private String pinyin;

    private Integer sort;

    private Date lastModifyTime;

    private Integer chinaAreaId;
    
    private List<City> citys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getChinaAreaId() {
        return chinaAreaId;
    }

    public void setChinaAreaId(Integer chinaAreaId) {
        this.chinaAreaId = chinaAreaId;
    }

	public List<City> getCitys() {
		return citys;
	}

	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
}