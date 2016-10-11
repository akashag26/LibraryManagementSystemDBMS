package com.dbms.entity;

import java.util.Date;

public class Checkout {
	
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public Date getCheckoutTime() {
		return checkoutTime;
	}
	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
	public Date getReservedUpto() {
		return reservedUpto;
	}
	public void setReservedUpto(Date reservedUpto) {
		this.reservedUpto = reservedUpto;
	}
	public Date getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}
	
	private int resourceId;
	private Date checkoutTime;
	private Date reservedUpto;
	private Date checkinTime;
	private int studentId;
	private int facultyId;

}
