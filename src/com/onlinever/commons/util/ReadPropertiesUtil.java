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

/**
 * 状态码信息工具类
 * @copyright(c) Qeeka 2013
 * @author dongrui
 * @date 2013-8-14
 * @version 2.0
 */
public class ReadPropertiesUtil {
	
	private static Logger log = Logger.getLogger(ReadPropertiesUtil.class);
	
	private static String resource = "classpath:com/onlinever/commons/property/status_code.properties";
	
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
		
	}
}
