package com.zevyirmiyahu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.beans.User;
import com.zevyirmiyahu.dao.UserDao;
import com.zevyirmiyahu.service.Service;

public class UserDaoImpl implements UserDao {

	User user;
	ReimburstmentRequest reimburstmentRequest;

	@Override
	public void createUser(User user) {
		// add user to database
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "INSERT INTO sys_users(is_Manager, first_name, last_name, "
					+ "address, phone, email, user_password) " + "VALUES(?, ?, ?, ?, ?, ?, ?)";
			String[] primaryKeyValues = { "user_id" };
			PreparedStatement stmt = conn.prepareStatement(sql, primaryKeyValues);
			stmt.setInt(1, 0); // all registering employees are NOT managers
			stmt.setString(2, user.getFirstName());
			stmt.setString(3, user.getLastName());
			stmt.setString(4, user.getAddress());
			stmt.setString(5, user.getPhone());
			stmt.setString(6, user.getEmail());
			stmt.setString(7, user.getPassword());
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			while (keys.next()) {
				int userID = keys.getInt(1);
				user.setUserID(userID);
			}
			Service.users.add(user); // add user to list
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// this should be in ReimburstmentRequestDaoImpl
//	@Override
//	public void submitReimburstmentRequest(int reimburstmentID, int amount, String date, int userID, String description) {
//		// add to database
//		try(Connection conn = ConnectionFactory.getConnection()) {
//			String sql = "INSERT INTO reimburstment_request(reimburstmentID, "
//								+ "amount, dates, status, user_id, description) "
//								+ "VALUES(?, ?, ?, ?, ?, ?)";
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			stmt.setInt(1, reimburstmentID);
//			stmt.setInt(2, amount);
//			stmt.setString(3, date);
//			stmt.setInt(4, userID);
//			stmt.setString(5, description);
//		} catch(SQLException e) {
//			e.printStackTrace();
//		}
//		// update requests lists
//		Service.pendingRequests.add(new ReimburstmentRequest(reimburstmentID, amount, date, 1, userID, description));	
//	}

//	@Override
//	public void viewPendingRequest(User user) {
//		for (ReimburstmentRequest request : Service.pendingRequests) {
//			if (request.getUserID() == user.getUserID()) {
//				// print info
//			}
//		}
//	}

	@Override
	public void viewResolvedRequest(User user) {
		for (ReimburstmentRequest request : Service.resolvedRequests) {
			if (request.getUserID() == user.getUserID()) {
				// print info
			}
		}
	}

	@Override
	public void viewInformation(User user) { // only gets information

	}

	@Override
	public void updateInformation(User user) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "UPDATE sys_users SET first_name = ?, last_name = ?, address = ?, phone = ? WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getAddress());
			stmt.setString(4, user.getPhone());
			stmt.setString(5, user.getEmail());
			stmt.executeUpdate();
			Service.update();
			Service.currentUser = findUser(user.getEmail(), user.getPassword()); // update current user with new info
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private User findUser(String email, String password) {
		for (User user : Service.users) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	// MANAGER SPECIFIC METHODS
	@Override
	public void viewAllEmployees() {
		for (User user : Service.users) {
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String phone = user.getPhone();
			boolean isManager = user.getIsManager();
			String result = "First Name: " + firstName + "\nLast Name: " + lastName + "\nphone: " + phone
					+ "\nManager: " + isManager;
			System.out.println(result);
		}
	}

	@Override
	public List<ReimburstmentRequest> viewEmployeeReimburstmentRequest(User user) {
		List<ReimburstmentRequest> employeeRequests = new ArrayList<>();
		for (ReimburstmentRequest request : Service.pendingRequests) {
			if (request.getUserID() == user.getUserID())
				employeeRequests.add(request);
		}
		for (ReimburstmentRequest request : Service.resolvedRequests) {
			if (request.getUserID() == user.getUserID())
				employeeRequests.add(request);
		}
		return employeeRequests; // all types
	}

//	@Override
//	public void viewAllPendingRequest() {
//		for (ReimburstmentRequest request : Service.pendingRequests) {
//			int reimburstmentID = request.getReimburstmentID();
//			int userID = request.getUserID();
//			int amount = request.getAmount();
//			String date = request.getDate();
//			String status = request.getStatus() == ;
//			String description = request.getDescription();
//			String result = "Reimburstment ID: " + reimburstmentID + "\nUser ID: " + userID + "\nAmount: " + amount
//					+ "\nDate: " + date + "\nDescription: " + description;
//			System.out.println(result);
//		}
//	}

	@Override
	public void viewAllResolvedRequest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void approveDenyRequest() {
	}

	@Override
	public void viewReceiptImages() {
	}
}
