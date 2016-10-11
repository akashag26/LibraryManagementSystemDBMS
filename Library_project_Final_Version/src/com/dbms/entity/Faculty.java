package com.dbms.entity;

public class Faculty {
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getHold() {
		return hold;
	}
	public void setHold(String hold) {
		this.hold = hold;
	}
	
	public String getFacultyId() {
		return facultyNumber;
	}
	public void setFacultyId(String facultyId) {
		this.facultyNumber = facultyId;
	}

	private String facultyNumber;
	private String lastName;
	private int categoryId;
	private String nationality;
	private String department;
	private String hold;
	private String firstName;	

}
