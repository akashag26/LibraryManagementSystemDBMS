package com.dbms.entity;

import java.util.Date;

public class CheckoutWait {
	
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getFacultyNumber() {
		return facultyNumber;
	}
	public void setFacultyNumber(int facultyNumber) {
		this.facultyNumber = facultyNumber;
	}

	private int resourceId;
	private Date timeStamp;
	private int studentId;
	private int facultyNumber;

}
