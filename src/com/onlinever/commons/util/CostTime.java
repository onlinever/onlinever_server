package com.onlinever.commons.util;

public class CostTime {

	private transient long start;
	
	public CostTime(){
		this.start = System.currentTimeMillis();
	}
	
	public void start(){
		this.start = System.currentTimeMillis();
	}
	
	public long cost(){
		return System.currentTimeMillis() - start;
	}
}
