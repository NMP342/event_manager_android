package com.example.demo.model;

public class Message {
	private int id, type, id_sender, send_or_receive;
	private String content, send_time;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	

	public Message(int type, int id_sender, String content, String send_time) {
		this.type = type;
		this.id_sender = id_sender;
		this.content = content;
		this.send_time = send_time;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId_sender() {
		return id_sender;
	}

	public void setId_sender(int id_sender) {
		this.id_sender = id_sender;
	}

	public int getSend_or_receive() {
		return send_or_receive;
	}


	public void setSend_or_receive(int send_or_receive) {
		this.send_or_receive = send_or_receive;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", type=" + type + ", id_sender=" + id_sender + ", content=" + content
				+ ", send_time=" + send_time + "]";
	}
	
}
