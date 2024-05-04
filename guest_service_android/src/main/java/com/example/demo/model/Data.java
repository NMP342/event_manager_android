package com.example.demo.model;

import java.util.List;

public class Data {
	private Event event;
	private List<Guest> guests;
	
	public Data() {
		// TODO Auto-generated constructor stub
	}

	public Data(Event event, List<Guest> guests) {
		super();
		this.event = event;
		this.guests = guests;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}
	
	
	
}
