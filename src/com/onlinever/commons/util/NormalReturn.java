package com.onlinever.commons.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


/**
 * 
 * @author Demon
 * 
 * @copyright (c) onlinever.com 2014
 */

public class NormalReturn {
	private Integer statusCode=200;

	private String msg="ok";
	
	@JSONField(name = "result")
	private Object result;	

	private long costTime;
	
	public NormalReturn(){}
	public NormalReturn(Integer statusCode,String msg){
		this.statusCode = statusCode;
		this.msg=msg;
	}
	public NormalReturn(Integer statusCode,Object result){
		this.statusCode = statusCode;
		this.result = result;
	}

	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCostTime() {
		return costTime;
	}
	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}
	
	public static NormalReturn check(JSONObject jso, String... params){
		for(String param: params){
			if(!jso.containsKey(param)){
				NormalReturn nr = new NormalReturn(401, param+" is null");
				return nr;
			}else if(jso.getString(param).isEmpty()){
				NormalReturn nr = new NormalReturn(401, param+" is empty");
				return nr;
			}
		}
		return null;
	}
}
