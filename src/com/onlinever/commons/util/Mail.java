package com.onlinever.commons.util;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class Mail {
	
	private static Logger log = Logger.getLogger(Mail.class);
	
    private MimeMessage mimeMsg;
    private Session session;
    private Properties props;
    private String username;
    private String password;
    private Multipart mp;
    public Mail(String smtp,String port) {
        setSmtpHost(smtp,port);
        createMimeMessage();
    }
    public void setSmtpHost(String hostName,String port) {
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.host", hostName);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable","true"); 
        props.put("mail.smtp.EnableSSL.enable","true");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.port", "25");
    }
    public boolean createMimeMessage() {
        try {
            session = Session.getDefaultInstance(props, null);
        } catch (Exception e) {
            log.error("获取邮件会话错误！" + e);
            return false;
        }
        try {
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();
 
            return true;
        } catch (Exception e) {
            log.error("创建MIME邮件对象失败！" + e);
            return false;
        }
    }
 
    /*定义SMTP是否需要验证*/
    public void setNeedAuth(boolean need) {
        if (props == null)
            props = System.getProperties();
        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
    }
    public void setNamePass(String name, String pass) {
        username = name;
        password = pass;
    }
 
    /*定义邮件主题*/
    public boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
            return true;
        } catch (Exception e) {
            log.error("定义邮件主题发生错误！");
            return false;
        }
    }
 
    /*定义邮件正文*/
    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=GBK");
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            log.error("定义邮件正文时发生错误！" + e);
            return false;
        }
    }
 
    /*设置发信人*/
    public boolean setFrom(String from) {
        try {
            mimeMsg.setFrom(new InternetAddress(from)); //发信人
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    /*定义收信人*/
    public boolean setTo(String to) {
        if (to == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    /*定义抄送人*/
    public boolean setCopyTo(String copyto) {
        if (copyto == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress
                    .parse(copyto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    /*发送邮件模块*/
    public boolean sendOut() {
        try {
        	log.info("发送邮件中...");
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), username, password);
            transport.sendMessage(mimeMsg, mimeMsg
            .getRecipients(Message.RecipientType.TO));
            log.info("发送成功！");
            transport.close();
            return true;
        } catch (Exception e) {
            log.error("邮件失败！" + e);
            return false;
        }
    }
 
    /*调用sendOut方法完成发送*/
    public static boolean sendAndCc(String smtp, String from, String to, String copyto,
        String subject, String content, String username, String password,String port) {
        Mail theMail = new Mail(smtp,port);
        theMail.setNeedAuth(true); // 验证
        if (!theMail.setSubject(subject))
            return false;
        if (!theMail.setBody(content))
            return false;
        if (!theMail.setTo(to))
            return false;
        if (!theMail.setCopyTo(copyto))
            return false;
        if (!theMail.setFrom(from))
            return false;
        theMail.setNamePass(username, password);
        if (!theMail.sendOut())
            return false;
        return true;
    }
    
    public static void sendMail(String email, String subject, String content) {
	    String smtp = ResourceUtils.getResString("mail.smtp");// smtp服务器
	    String from = ResourceUtils.getResString("mail.from");// 邮件显示名称
	    String copyto = "";// 抄送人邮件地址
	    String port = ResourceUtils.getResString("mail.port");
	    String username = ResourceUtils.getResString("mail.uname");// 发件人真实的账户名
	    String password = ResourceUtils.getResString("mail.pwd");// 发件人密码
	    Mail.sendAndCc(smtp, from, email, copyto, subject, content, username, password, port);
	}
    
    public static void main(String[] args) {
		sendMail("946894520@qq.com","onlinever.com(注册邮箱验证)" , ResourceUtils.getString("mail.register","946894520@qq.com","945624" ));
	}
}