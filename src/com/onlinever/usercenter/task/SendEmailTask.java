package com.onlinever.usercenter.task;

import com.onlinever.commons.util.Mail;

/**
 * @author Demon
 * 
 * @copyright (c) onlinever.com 2014
 */
public class SendEmailTask implements ITask {
	private static final long serialVersionUID = 3443410189554609453L;
	
	private String email;
	private String subject;
	private String content;
	
	public SendEmailTask(String email,String subject,String content){
		this.email = email;
		this.subject = subject;
		this.content = content;
	}
	
	@Override
	public void run() {
		Mail.sendMail(email, subject, content);
	}
}
