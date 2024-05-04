package com.example.demo.model;

public class SMS {
	private String phone, message;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SMS [phone=" + phone + ", message=" + message + "]";
	}
}
