package com.dbms.entity;

import java.util.Date;

public class BookReserved {

	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public int getFacultyNumber() {
		return facultyNumber;
	}
	public void setFacultyNumber(int facultyNumber) {
		this.facultyNumber = facultyNumber;
	}
	public int getCourseName() {
		return courseName;
	}
	public void setCourseName(int courseName) {
		this.courseName = courseName;
	}
	public Date getResStartTime() {
		return resStartTime;
	}
	public void setResStartTime(Date resStartTime) {
		this.resStartTime = resStartTime;
	}
	public Date getResEndTime() {
		return resEndTime;
	}
	public void setResEndTime(Date resEndTime) {
		this.resEndTime = resEndTime;
	}
	
	private String ISBN;
	private int facultyNumber;
	private int courseName;
	private Date resStartTime;
	private Date resEndTime;
	
	
}
