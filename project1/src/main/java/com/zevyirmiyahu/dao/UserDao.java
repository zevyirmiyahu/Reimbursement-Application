package com.zevyirmiyahu.dao;

import com.zevyirmiyahu.beans.User;

public interface UserDao {
	public void createUser(int userID, boolean isManager, String firstName, 
			String lastName, String address, String phone, String email, String password);
	public void submitReimburstmentRequest(int reimburstmentID, int amount, String date, int userID, String description);
	public void viewPendingRequest(User user);
	public void viewResolvedRequest(User user);
	public void viewInformation(User user);
	public void updateInformation(User user);
	public void viewHomepage(User user);
	
	// ADDITIONAL MANAGER ROLES
	public void viewAllEmployees();
	public void viewEmployeeReimburstmentRequest(); // view a particular employee request
	public void viewAllPendingRequest();
	public void viewAllResolvedRequest();
	public void approveDenyRequest();
	public void viewReceiptImages();
}
