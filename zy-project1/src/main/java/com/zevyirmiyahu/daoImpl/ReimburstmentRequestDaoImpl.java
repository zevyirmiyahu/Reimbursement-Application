package com.zevyirmiyahu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.dao.ReimburstmentRequestDao;
import com.zevyirmiyahu.service.Service;

public class ReimburstmentRequestDaoImpl implements ReimburstmentRequestDao {

	@Override
	public void submitReimburstmentRequest(ReimburstmentRequest request) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "INSERT INTO reimburstment_request(amount, dates, status, user_id, request_description) " 
								+ "VALUES(?, ?, ?, ?, ?)";
			String[] primaryKeyValues = { "reimburstment_id" };
			PreparedStatement stmt = conn.prepareStatement(sql, primaryKeyValues);
			stmt.setInt(1, request.getAmount());
			stmt.setString(2, request.getDate());
			stmt.setInt(3, 1); // all submited request will have initial pending status
			stmt.setInt(4, Service.getUserDatabaseID(Service.currentUser));
			stmt.setString(5, request.getDescription());
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			while(keys.next()) {
				int reimburstmentID = keys.getInt(1);
				request.setReimburstmentID(reimburstmentID);
				Service.pendingRequests.add(request);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void resolveRequest(ReimburstmentRequest request) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String sql = "UPDATE reimburstment_request SET status = ? WHERE reimburstment_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, request.getStatus().equals("Approved") ? 2 : 3);
			stmt.setInt(2, request.getReimburstmentID());
			stmt.executeUpdate();
			Service.update(); // update all lists
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
