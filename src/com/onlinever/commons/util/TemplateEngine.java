package com.onlinever.commons.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class TemplateEngine {
	private VelocityEngine velocityEngine;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	@SuppressWarnings("rawtypes")
	public String build(String pathname, String templatefile, Map model){
		StringWriter writer = new StringWriter();
        try {
            VelocityEngineUtils.mergeTemplate(velocityEngine, templatefile, "UTF-8", model, writer);
        } catch (VelocityException e) {
            e.printStackTrace();
        }
        String htmlStr = writer.toString();
        if(pathname != null && !"".equals(pathname)){
        	FileUtils.write(pathname, htmlStr);
        }
        try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlStr;
	}
}
