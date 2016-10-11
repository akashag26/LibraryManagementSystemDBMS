package com.dbms.entity;

import java.util.List;

public class Journal {
	
	public String getISSN() {
		return ISSN;
	}
	public void setISSN(String iSSN) {
		ISSN = iSSN;
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
		return Ecopy;
	}
	public void setEcopy(String ecopy) {
		Ecopy = ecopy;
	}

	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Author> getAuthorNames() {
		return authorNames;
	}
	public void setAuthorNames(List<Author> authorNames) {
		this.authorNames = authorNames;
	}

	private List<Author> authorNames;
	private String libraryName;
	private String categoryName;
	private String Ecopy;
	private String ISSN;
	private String title;
	private String year;
	private int libraryId;
	private int categoryId;
	private int resourceId;

}
