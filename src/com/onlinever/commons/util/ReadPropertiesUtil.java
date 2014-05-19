package com.onlinever.commons.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.fastjson.JSONObject;

/**
 * 状态码工具类
 * @author Demon
 * 
 * @copyright (c) onlinever.com 2014
 */
public class ReadPropertiesUtil {
	
	private static Logger log = Logger.getLogger(ReadPropertiesUtil.class);
	
	private static String resource = "classpath:status_code.properties";
	
	private static Map<String,String> configMap = new HashMap<String,String>();
	
	public static String getErrorMessage(int statusCode) {
		try {
			return configMap.get(String.valueOf(statusCode));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void loadStatusCodeMap() {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		Resource res = resourceResolver.getResource(resource);
		Properties props = null;
		try {
			props = new Properties();
			props.load(new InputStreamReader(res.getInputStream(),"UTF-8"));
		} catch (IOException e) {
			log.error("Loading MultipleMap properties failed", e);
		}
		if(props != null) {
			Enumeration<?> en = props.propertyNames();
			while(en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = props.getProperty(key);
				configMap.put(key.trim(), value.trim());
			}
		}
	}
	
	public static void main(String[] args){
		String content="{\"brand_no\":\"jycy,sy\",\"unit_rank\":\"2\",\"package\":\"2\"}";
		JSONObject json = JSONObject.parseObject(content);
		@SuppressWarnings("unchecked")
		Map<String,Object> map = JSONObject.toJavaObject(json, HashMap.class);
		System.out.println(map.get("brand_no"));
		System.out.println(json.get("brand_no"));
	}
}
