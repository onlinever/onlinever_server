package com.onlinever.commons.content;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import com.onlinever.commons.util.ReadPropertiesUtil;

public class ServiceChainContextLoader extends ContextLoaderListener {

	private static final Logger log = Logger.getLogger(ServiceChainContextLoader.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		   super.contextInitialized(event);
		   ReadPropertiesUtil.loadStatusCodeMap();
    }	
	
	@Override
	public ContextLoader createContextLoader() {
		log.info("returning ServiceChainContextLoader");
		return new ServiceChainContextLoader();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		new Thread(){
			public void run(){
		        try {
					Thread.sleep(300000);
			        System.exit(0);
				} catch (Exception e) {
				}
			}
		}.start();
		super.contextDestroyed(event);
	}
}
