package com.zevyirmiyahu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.beans.User;
import com.zevyirmiyahu.dao.UserDao;
import com.zevyirmiyahu.service.Service;

public class UserDaoImpl implements UserDao {

	User user;
	ReimburstmentRequest reimburstmentRequest;

	@Override
	public void createUser(int userID, boolean isManager, String firstName, String lastName, 
			String address, String phone, String email, String password) {
		// add user to database
		try(Connection conn = ConnectionFactory.getConnection()) {
			String sql = "INSERT INTO users(user_id, is_Manager, first_name, last_name, "
								+ "address, phone, email, user_password) "
								+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			stmt.setInt(2, isManager == true ? 1 : 0);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);
			stmt.setString(5, address);
			stmt.setString(6,  phone);
			stmt.setString(7, email);
			stmt.setString(8, password);
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// add user to list
		Service.users.add(new User(userID, isManager, firstName, lastName, address, phone, email, password));
	}

	@Override
	public void submitReimburstmentRequest(int reimburstmentID, int amount, String date, int userID, String description) {
		// add to database
		try(Connection conn = ConnectionFactory.getConnection()) {
			String sql = "INSERT INTO reimburstment_request(reimburstmentID, "
								+ "amount, dates, status, user_id, description) "
								+ "VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, reimburstmentID);
			stmt.setInt(2, amount);
			stmt.setString(3, date);
			stmt.setInt(4, userID);
			stmt.setString(5, description);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// update requests lists
		Service.pendingRequests.add(new ReimburstmentRequest(reimburstmentID, amount, date, 1, userID, description));	
	}

	@Override
	public void viewPendingRequest(User user) {
		for(ReimburstmentRequest request : Service.pendingRequests) {
			if(request.getUserID() == user.getUserID()) {
				// print info
			}
		}
	}

	@Override
	public void viewResolvedRequest(User user) {
		for(ReimburstmentRequest request : Service.resolvedRequests) {
			if(request.getUserID() == user.getUserID()) {
				// print info
			}
		}
	}

	@Override
	public void viewInformation(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateInformation(User user) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void viewHomepage(User user) {
		
	}
	
	// MANAGER SPECIFIC METHODS
	@Override
	public void viewAllEmployees() {
		for(User user : Service.users) {
			int userID = user.getUserID();
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String phone = user.getPhone();
			boolean isManager = user.getIsManager();
			String result = "User ID: " + userID
									+ "\nFirst Name: " + firstName
									+ "\nLast Name: " + lastName
									+"\nphone: " + phone
									+"\nManager: " + isManager;
			System.out.println(result);
		}
	}
	
	@Override
	public void viewEmployeeReimburstmentRequest() {
		
	}
	
	@Override
	public void viewAllPendingRequest() {
		for(ReimburstmentRequest request : Service.pendingRequests) {
			int reimburstmentID = request.getReimburstmentID();
			int userID = request.getUserID();
			int amount = request.getAmount();
			String date = request.getDate();
			String status = request.getStatus();
			String description = request.getDescription();
			String result = "Reimburstment ID: " + reimburstmentID
									+ "\nUser ID: " + userID
									+ "\nAmount: " + amount
									+ "\nDate: " + date
									+ "\nDescription: " + description;
			System.out.println(result);
		}
	}

	@Override
	public void viewAllResolvedRequest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void approveDenyRequest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewReceiptImages() {
		// TODO Auto-generated method stub
		
	}
}
