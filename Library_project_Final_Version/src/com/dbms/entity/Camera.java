package com.dbms.entity;

import java.util.Date;

public class Camera {
	
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
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
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public String getMemoryAvailable() {
		return memoryAvailable;
	}

	public void setMemoryAvailable(String memoryAvailable) {
		this.memoryAvailable = memoryAvailable;
	}
	
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Date getReservationStartTime() {
		return reservationStartTime;
	}
	public void setReservationStartTime(Date reservationStartTime) {
		this.reservationStartTime = reservationStartTime;
	}

	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}

	private String cameraId;
	private int libraryId;
	private int categoryId;
	private String manufacturer;
	private String model;
	private String configuration;
	private String memoryAvailable;
	private int reservationId;
	private Date reservationStartTime;
	private int fine;
	
}
