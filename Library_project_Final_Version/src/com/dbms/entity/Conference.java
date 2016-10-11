package com.dbms.entity;

import java.util.List;

public class Conference {

	
	public List<Author> getAuthorNames() {
		return authorNames;
	}
	public void setAuthorNames(List<Author> authorName) {
		this.authorNames = authorName;
	}
	
	public String getConferenceNumber() {
		return conferenceNumber;
	}
	public void setConferenceNumber(String conferenceNumber) {
		this.conferenceNumber = conferenceNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(int libraryId) {
		this.libraryId = libraryId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getEcopy() {
		return ecopy;
	}
	public void setEcopy(String ecopy) {
		this.ecopy = ecopy;
	}
	public String getConferenceName() {
		return conferenceName;
	}
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}
	
	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	public String getCaetgoryName() {
		return caetgoryName;
	}
	public void setCaetgoryName(String caetgoryName) {
		this.caetgoryName = caetgoryName;
	}

	private String conferenceNumber;
	private String title;
	private String year;
	private int libraryId;
	private int categoryId;
	private int resourceId;
	private String ecopy;
	private String conferenceName;
	private String libraryName;	
	private String caetgoryName;
	private List<Author> authorNames;	
	
}
