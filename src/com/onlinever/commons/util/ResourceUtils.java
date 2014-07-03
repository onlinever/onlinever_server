package com.onlinever.commons.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 读取资源文件工具类
 * @author Demon
 * 
 * @copyright (c) onlinever.com 2014
 */
public class ResourceUtils {
	private static ResourceBundle bundle;
	private static ResourceBundle resBundle;
	/**
	 * default language English
	 */
	private static String LANGUAGE = "_en";
	
	static {
		setBundle();
		resBundle = ResourceBundle.getBundle("com/onlinever/commons/resource/resource");
	}
	/**
	 * update default Language
	 * @param language
	 */
	public static void setLanguage(String language){
		LANGUAGE = language;
		setBundle();
		
	}
	
	private static void setBundle(){
		//TODO More languages
		Locale local = Locale.CHINA;
		if(LANGUAGE.equals("_en")){
			local = Locale.ENGLISH;
		}
		bundle = ResourceBundle.getBundle("com/onlinever/commons/resource/text"+LANGUAGE, local);
	}
	
	public static String getString(String label) {
		if(bundle!=null) {
			try {
				return bundle.getString(label);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	public static String getString(String label, Object... args) {
		if(bundle!=null) {
			try {
				String str = bundle.getString(label);
				if(str!=null) {
					MessageFormat form = new MessageFormat(str);
					str = form.format(args);
					return str;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static String[] getArray(String label,String regex) {
		if(bundle!=null) {
			try {
				return bundle.getString(label).split(regex);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String getResString(String label){
		if(resBundle!=null) {
			try {
				return resBundle.getString(label);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String getResString(String label,Object... args){
		if(resBundle!=null) {
			try {
				String str = resBundle.getString(label);
				if(str!=null) {
					MessageFormat form = new MessageFormat(str);
					str = form.format(args);
					return str;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String []args ) {
		ResourceUtils.setLanguage(Utilities.ENGLISH);
		System.out.println(ResourceUtils.getString("country"));
	}
	
}
