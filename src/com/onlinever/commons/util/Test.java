package com.onlinever.commons.util;

import java.lang.reflect.*;




public class Test {
	public static void main(String[] args) {
		try {
			Demo demo = new Demo();
			Method method = demo.getClass().getMethod("print",Integer.class);
			method.setAccessible(true);
			method.invoke(demo,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class Demo{
	private void print(Integer col){
		System.out.println(String.format("col = %s ,row = %s",1,2));
	}
}
