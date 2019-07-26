package com.zevyirmiyahu.dao;

import java.util.List;

import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.beans.User;

public interface UserDao {
	public void createUser(User user);
	//public void viewPendingRequest(User user);
	public void viewResolvedRequest(User user);
	public void viewInformation(User user);
	public void updateInformation(User user);
	
	// ADDITIONAL MANAGER ROLES
	public void viewAllEmployees();
	public List<ReimburstmentRequest> viewEmployeeReimburstmentRequest(User user); // view a particular employee request
	//public void viewAllPendingRequest();
	public void viewAllResolvedRequest();
	public void approveDenyRequest();
	public void viewReceiptImages();
}
