package com.onlinever.usercenter.model;

import java.util.Date;
import java.util.List;

public class City {
    private Integer id;

    private String name;

    private String pinyin;

    private Integer sort;

    private Integer provinceId;

    private Date lastModifyTime;
    
    private List<Cityregion> Cityregions;

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

	public List<Cityregion> getCityregions() {
		return Cityregions;
	}

	public void setCityregions(List<Cityregion> cityregions) {
		Cityregions = cityregions;
	}
}