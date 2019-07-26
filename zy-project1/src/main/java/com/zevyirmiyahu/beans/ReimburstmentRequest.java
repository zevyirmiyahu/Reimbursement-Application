package com.zevyirmiyahu.beans;

import java.sql.Blob;

/*
 * In database status is defined by
 * 			reimburstmend_id		status
 * 						1						Pending
 * 						2						Approved
 * 						3						Rejected
 */

public class ReimburstmentRequest {
	
	private int reimburstmentID;
	private int amount;
	private String date;
	private String status;
	private int userID;
	private String description; // description of request
	private String[] statusTypes = {"Pending", "Approved", "Rejected"};
	private byte[] imageBytes; // array holds bytes of the image
	private Blob blob; // store image
	
	public ReimburstmentRequest(int amount, String date, 
			int status, int userID, String description) {
		super();
		this.amount = amount;
		this.date = date;
		this.userID = userID;
		this.description = description;
		this.status = statusTypes[status - 1]; // status have values 1,2,3 minus one to reindex array
	}
	
	public void setBlob(Blob blob) {
		this.blob = blob;
	}
	
	public Blob getImageBytes() {
		return blob;
	}

	public int getReimburstmentID() {
		return reimburstmentID;
	}

	public void setReimburstmentID(int reimburstmentID) {
		this.reimburstmentID = reimburstmentID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}
}
