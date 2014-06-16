package com.onlinever.commons.util;

import com.alibaba.fastjson.JSONObject;

public class Test {
	public static void main(String[] args) {
		JSONObject j = new JSONObject();
		if(j.getString("key")==null){
			System.out.println(123);
		}
		System.out.println(j.getString("key"));
	}
}
