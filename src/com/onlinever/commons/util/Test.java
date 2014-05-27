package com.onlinever.commons.util;

import java.util.Calendar;


public class Test {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getCurrentDate());
		cal.add(Calendar.DATE, 3 * -1);
		System.out.println(cal.getTime());
		cal.add(Calendar.DATE, 3 -1);
		System.out.println(cal.getTime());
	}
}
