package com.util.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;



public class JavaMailConf {


	
	public JavaMailConf(){
		
	}
	
	public Session Config(){
	
	Properties props = new Properties();
	/** Par�metros de conex�o com servidor Gmail */
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
			"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");

	Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("celoguimaraes@gmail.com",
							"@ssEmbly9883");
				}
			});

	/** Ativa Debug para sess�o */
	session.setDebug(true);
	return session;
	}
}
