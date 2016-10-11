package com.dbms.entity;

public class CoursesTaught {
	
	public int getFacultyNumber() {
		return facultyNumber;
	}
	public void setFacultyNumber(int facultyNumber) {
		this.facultyNumber = facultyNumber;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	private String courseName;
	private int facultyNumber;	

}
