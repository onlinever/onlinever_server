package com.onlinever.commons.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 解析器
 * @author sunwuhua
 * @date   2012-2-17
 */
public class StringUtil {
	
	private static final String DEFAULT_SEPERATOR = ";";
	
	public StringUtil(){
		
	}
	
	/**
	 * 解析特定分隔符字符串
	 * @param targetStr 被解析字符串
	 * @param seperator 分隔符
	 * @return 
	 */
	public static List<String> parseString(String targetStr, String seperator){
		List<String> list = new ArrayList<String>();
		
		if(targetStr == null || "".equals(targetStr))
			return list;
		
		if(targetStr.indexOf(seperator) < 0) {
			list.add(targetStr);
			return list;
		}
		
		StringTokenizer st = new StringTokenizer(targetStr,seperator);
		while(st.hasMoreTokens())
			list.add(st.nextToken());
		
		return list;
	}
	
	/** 
     * 对特殊的html字符进行编码 
     * add by yangtao 
     * @param message 
     * @return 
     */  
    public static String filterString(String message) { 
        if(!"".equals(message)&&null!=message){
	        char content[] = new char[message.length()];  
	        message.getChars(0, message.length(), content, 0);  
	        StringBuilder result = new StringBuilder(content.length + 50);  
	        for (int i = 0; i < content.length; i++) {  
	            switch (content[i]) {  
	            case '<':  
	                result.append("&lt;");  
	                break;  
	            case '>':  
	                result.append("&gt;");  
	                break;  
	            case '&':  
	                result.append("&amp;");  
	                break;  
	            case '\"':  
	                result.append("&quot;");  
	                break;  
	            default:  
	                result.append(content[i]);  
	            }  
	        } 
	        return result.toString();
        }else{
        	return "";
        }
    }
    /***
	 * @param  String 
	 * @return List
	 * 需要一到工具类方法**/
	public static List<String> splitByFlg(String serviceType,String flg){
		List<String> serviceTypeList=new ArrayList<String>();
		if(!"".equals(serviceType)||null!=serviceType){
			while(serviceType.indexOf(flg)!=-1){
				
				serviceTypeList.add(serviceType.substring(0, serviceType.indexOf(flg)));
				serviceType=serviceType.substring(serviceType.indexOf(flg)+1);
			}
			if(serviceType.length()>=1){
				serviceTypeList.add(serviceType.substring(0));
			}
		}
		return serviceTypeList;
	}
	/**
	 * 解析用分号分隔的字符串
	 * @param targetStr 被解析字符串
	 * @return 
	 */
	public static List<String> parseString(String targetStr) {
		return parseString(targetStr,DEFAULT_SEPERATOR);
	}
	
	
	public static String filterScript(String str) {
		//Pattern pattern = new Pattern();
		return "";
	}
	
	//add by huliang
	//字符串不解析html标签
	public static String showHtml(String str){
		return str.replace("<", "&lt;").replace(">", "&gt;");
	}
	
	//输入框中字符串中有'"'过滤
	public static String showInput(String str){
		return str.replace("\"", "&quot;");
	}
	
	public static void main(String[] args) {
		/*
		String str  = "10";
		String str1 ="10;20";
		String str2 ="ccc,ddd,eee";
		
		List<String> list = parseString(str);
		for(String s: list)
			System.out.println(s);
		
		System.out.println();
		
		list = parseString(str1);
		for(String s: list)
			System.out.println(s);
		
		System.out.println();
		
		list = parseString(str2,",");
		for(String s: list)
			System.out.println(s);*/
		
		String s ="   ";
	    System.out.println(notNullandBlank(s));
	}
	
	public static String filterNonWord(String keyword){
		keyword = keyword.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]", " ").trim();
		return keyword;
	}
	
	public static Integer currentTime(){
		return new Long(System.currentTimeMillis()/1000).intValue();
	}
	
	public static String fromUnixTimeStamp(String timestamp){
		try{
			Long time = Long.parseLong(timestamp)*1000;
			timestamp = DateUtil.getDatetimeStr(time);
		}catch(NumberFormatException e){
		}
		return timestamp;
	}
	
	public static boolean notNullandBlank(String str){
    	return ( str != null && !"".equals(str.trim()) );
    }
	
	public static String md5(String src) {
        byte[] unencodedPassword;
		try {
			unencodedPassword = src.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return src;
		}
        
        MessageDigest md = null;
        
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance("md5");
        } catch (Exception e) {
            return src;
        }
        
        md.reset();
        
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);
        
        // now calculate the hash
        byte[] encodedPassword = md.digest();
        
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        
        return buf.toString();
    }
}
