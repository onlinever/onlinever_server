package com.onlinever.commons.util;

public class Test {
	public static void main(String[] args) {
		String a = Utilities.getRandomNumber(6);
		String b = Utilities.getRandomPwd(6);
		String s = "";
		for (int i = 0; i < 6; i++) {
			s=s+b.charAt(i)+a.charAt(i);
		}
		System.out.println(s);
	}
}
