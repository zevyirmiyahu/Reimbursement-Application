package com.zevyirmiyahu.service;

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
	
	private User user;
	private UserDaoImpl userImpl;
	public static List<User> users = new ArrayList<>(); // list are loaded at start of application and updated when needed
	public static List<ReimburstmentRequest> pendingRequests = new ArrayList<>();
	public static List<ReimburstmentRequest> resolvedRequests = new ArrayList<>();
	
	public Service() {
		initialize();
	}
	
	// start of application get all information from database
	private void initialize() {
		userImpl = new UserDaoImpl();
		loadUsers();
		loadPendingRequests();
		loadResolvedRequests();
	}
	
	private void loadUsers() {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM users";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int userID = result.getInt("user_id");
				boolean isManager = result.getInt("is_Manager") == 0 ? false : true;
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("address");
				String phone = result.getString("phone");
				String email = result.getString("email");
				String password = result.getString("user_password");
				users.add(new User(userID, isManager, firstName, lastName, address, phone, email, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadPendingRequests() {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM reimburstment_request WHERE status = 'Pending'";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int reimburstmentID = result.getInt("reimburstment_id");
				int amount = result.getInt("amount");
				String date = result.getString("dates");
				int status = result.getInt("status");
				int userID = result.getInt("user_id");
				String description = result.getString("description");
				pendingRequests.add(new ReimburstmentRequest(reimburstmentID, amount, date, status, userID, description));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadResolvedRequests() {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM reimburstment_request WHERE status != 'Pending'";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int reimburstmentID = result.getInt("reimburstment_id");
				int amount = result.getInt("amount");
				String date = result.getString("dates");
				int status = result.getInt("status");
				int userID = result.getInt("user_id");
				String description = result.getString("description");
				resolvedRequests.add(new ReimburstmentRequest(reimburstmentID, amount, date, status, userID, description));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void register(int userID, boolean isManager, String firstName, String lastName, String address, 
			String phone, String email, String password) {
		boolean isValid = isValidRegister(userID, firstName, lastName, address, email);
		if(isValid) userImpl.createUser(userID, isManager, firstName, lastName, address, phone, email, password); 
		
	}
	
	private boolean isValidRegister(int userID, String firstName, String lastName, String address, String email) {
		// no null values
		if(firstName.equals(null) || lastName.equals(null) || address.equals(null) || email.equals(null)) {
			System.out.println("Invalid input");
			return false;
		}
		// no duplicate user
		else {			
			for(User user : users) {
				if(user.getEmail().equals( email)) {
					System.out.println("User email taken");
					return false;
				}
			}
		}
		System.out.println("Registration successful");
		return true;
	}
	
	

	public void update(Object object) {
	}

	public void login(String email, String password) {
		for(User user : users) {
			if(user.getEmail().equals(email)) {
				if(user.getPassword().equals(password)) {
					this.user = user;
				}
				else {
					System.out.println("Invalid password");
				}
			}
			else {
				System.out.println("Invalid email");
			}
		}
	}

	public void logout() {
		this.user = null;
	}
}
