package com.onlinever.commons.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Demon
 * 
 * @copyright (c) onlinever.com 2014
 */
public class ResourceUtils {
	private static ResourceBundle bundle;
	
	static {
		String language = ResourceBundle.getBundle("com/onlinever/commons/i18n/i18n").getString("language");
		Locale local = Locale.CHINA;
		if(language.equals("_en")){
			local = Locale.ENGLISH;
		}
		bundle = ResourceBundle.getBundle("com/onlinever/commons/i18n/text"+language, local);
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
	
	public static void main(String []args ) {
		System.out.println(ResourceUtils.getString("country"));
	}
	
}
