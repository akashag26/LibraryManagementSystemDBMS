package com.dbms.entity;

import java.util.List;

public class Book {

	public static String[] getColumnNames() {
		return columnNames;
	}
	public static void setColumnNames(String[] columnNames) {
		Book.columnNames = columnNames;
	}
	
	private String ISBN;
	private String title;
	private String edition;
	private List<Author> authorNames;
	private String yearOfPublication;
	private String publisher;
	private int libId;
	private int categoryId;
	private String category;
	private String library;
	private int resourceId;
	private int fine;
	private String ecopy;
	
	public String getEcopy() {
		return ecopy;
	}
	public void setEcopy(String ecopy) {
		this.ecopy = ecopy;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public int getResourceID() {
		return resourceId;
	}
	public void setResourceID(int ResourceId) {
		this.resourceId = ResourceId;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}

	static String[] columnNames = {"ISBN","Title","Edition",
			"Author Names","Year Of Publication","Publisher",
			"LibId", "CategoryId","Category","Library","ResourceId"};
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public List<Author> getAuthorNames() {
		return authorNames;
	}
	public void setAuthorNames(List<Author> authorName) {
		this.authorNames = authorName;
	}
	public String getYearOfPublication() {
		return yearOfPublication;
	}
	public void setYearOfPublication(String yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getLibId() {
		return libId;
	}
	public void setLibId(int libId) {
		this.libId = libId;
	}
	
}
