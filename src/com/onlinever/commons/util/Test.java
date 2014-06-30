package com.onlinever.commons.util;

import java.sql.Timestamp;



public class Test {
	public static void main(String[] args) {
		System.out.println(new Timestamp(DateUtil.parseStringToDateTime("2014-06-25 23:59:59").getTime()));
	}
}
