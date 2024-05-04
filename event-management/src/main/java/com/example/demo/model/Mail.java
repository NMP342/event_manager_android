package com.example.demo.model;

public class Mail {
	private String recipient, subject, body, otp;
	private long send_time;
	
	public Mail() {
		// TODO Auto-generated constructor stub
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getSend_time() {
		return send_time;
	}

	public void setSend_time(long send_time) {
		this.send_time = send_time;
	}

	@Override
	public String toString() {
		return "Mail [recipient=" + recipient + ", subject=" + subject + ", body=" + body + ", otp=" + otp
				+ ", send_time=" + send_time + "]";
	}

}
