package com.onlinever.commons;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/*
 *   @copyright (c) Qeeka 2011 
 * @author YinChunhui    Jul 25, 2011 
 */
public class ExecutorFactory {

	public static ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	public static ExecutorService fixedExecutor = Executors.newFixedThreadPool(10);
	public static ExecutorService cachedExecutor = Executors.newCachedThreadPool();
	
}
