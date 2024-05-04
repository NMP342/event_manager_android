package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Guest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int idEvent;
	private String name;
	private String email;
	private String phoneNumber;
	private int confirm;
	
	public Guest() {
		// TODO Auto-generated constructor stub
	}

	public Guest(int id, int idEvent, String name, String email, String phoneNumber, int confirm) {
		super();
		this.id = id;
		this.idEvent = idEvent;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.confirm = confirm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

	@Override
	public String toString() {
		return "Guest [id=" + id + ", idEvent=" + idEvent + ", name=" + name + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", confirm=" + confirm + "]";
	}

}
