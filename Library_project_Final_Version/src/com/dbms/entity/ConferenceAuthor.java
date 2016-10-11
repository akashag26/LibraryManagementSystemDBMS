package com.dbms.entity;

public class ConferenceAuthor {

	public String getConferenceNumber() {
		return conferenceNumber;
	}
	public void setConferenceNumber(String conferenceNumber) {
		this.conferenceNumber = conferenceNumber;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	private String conferenceNumber;
	private int authorId;
	
	
}
