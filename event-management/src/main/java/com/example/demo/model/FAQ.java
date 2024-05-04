package com.example.demo.model;

public class FAQ {
	private int id;
	private String question;
	
	public FAQ() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "FAQ [id=" + id + ", question=" + question + "]";
	}
	
}
