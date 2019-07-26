package com.zevyirmiyahu.service;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.beans.User;
import com.zevyirmiyahu.daoImpl.ConnectionFactory;
import com.zevyirmiyahu.daoImpl.UserDaoImpl;

public class Service {
	
	public static User currentUser;
	private UserDaoImpl userImpl;
	public static List<User> users = new ArrayList<>(); // list are loaded at start of application and updated when needed
	public static List<ReimburstmentRequest> pendingRequests = new ArrayList<>();
	public static List<ReimburstmentRequest> resolvedRequests = new ArrayList<>();
	
	public Service() {
		//initialize();
	}
	
	// start of application get all information from database
	private static void initialize() {
		//userImpl = new UserDaoImpl();
		loadUsers();
		loadPendingRequests();
		loadResolvedRequests();
	}
	
	// ensures all lists match current database
	public static void update() {
		users.clear();
		pendingRequests.clear();
		resolvedRequests.clear();
		loadUsers();
		loadPendingRequests();
		loadResolvedRequests();
	}
	
	private static void loadUsers() {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM sys_users";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				boolean isManager = result.getInt("is_Manager") == 0 ? false : true;
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("address");
				String phone = result.getString("phone");
				String email = result.getString("email");
				String password = result.getString("user_password");
				users.add(new User(isManager, firstName, lastName, address, phone, email, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void loadPendingRequests() {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM reimburstment_request WHERE status = 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int reimbursementId = result.getInt("reimburstment_id");
				int amount = result.getInt("amount");
				String date = result.getString("dates");
				int status = result.getInt("status");
				int userID = result.getInt("user_id");
				String description = result.getString("request_description");
				ReimburstmentRequest reimbursement = new ReimburstmentRequest(amount, date, status, userID, description);
				reimbursement.setReimburstmentID(reimbursementId);
				pendingRequests.add(reimbursement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadResolvedRequests() {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM reimburstment_request WHERE status != 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int reimbursementId = result.getInt("reimburstment_id");
				int amount = result.getInt("amount");
				String date = result.getString("dates");
				int status = result.getInt("status");
				int userID = result.getInt("user_id");
				String description = result.getString("request_description");
				ReimburstmentRequest reimbursement = new ReimburstmentRequest(amount, date, status, userID, description);
				reimbursement.setReimburstmentID(reimbursementId);
				resolvedRequests.add(reimbursement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getUserDatabaseID(User user) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM sys_users WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getEmail());
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				return result.getInt("user_id");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
//	public void register(boolean isManager, String firstName, String lastName, String address, 
//			String phone, String email, String password) {
//		boolean isValid = isValidRegister(firstName, lastName, address, email);
//		if(isValid) userImpl.createUser(isManager, firstName, lastName, address, phone, email, password); 
//		
//	}
	
//	private boolean isValidRegister(String firstName, String lastName, String address, String email) {
//		// no null values
//		if(firstName.equals(null) || lastName.equals(null) || address.equals(null) || email.equals(null)) {
//			System.out.println("Invalid input");
//			return false;
//		}
//		// no duplicate user
//		else {			
//			for(User user : users) {
//				if(user.getEmail().equals( email)) {
//					System.out.println("User email taken");
//					return false;
//				}
//			}
//		}
//		System.out.println("Registration successful");
//		return true;
//	}

}
