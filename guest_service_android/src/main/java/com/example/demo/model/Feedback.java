package com.example.demo.model;

public class Feedback {
	private Event event;
	private String linkForm;
	
	public Feedback() {
		// TODO Auto-generated constructor stub
	}

	public Feedback(Event event, String linkForm) {
		super();
		this.event = event;
		this.linkForm = linkForm;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getLinkForm() {
		return linkForm;
	}

	public void setLinkForm(String linkForm) {
		this.linkForm = linkForm;
	}
	
	
}
