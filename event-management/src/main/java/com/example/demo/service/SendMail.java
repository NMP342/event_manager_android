package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMail(String toMail, String subject, String body) {
		SimpleMailMessage sml = new SimpleMailMessage();
		sml.setFrom("tm425625@gmail.com");
		sml.setTo(toMail);
		sml.setText(body);
		sml.setSubject(subject);
		
		mailSender.send(sml);
		
		System.out.println("Mail send successfully.....");
	}
}
