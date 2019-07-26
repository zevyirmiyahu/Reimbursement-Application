package com.zevyirmiyahu.servlets;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.daoImpl.ReimburstmentRequestDaoImpl;
import com.zevyirmiyahu.service.Service;

public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RequestServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int amount =  Integer.parseInt(request.getParameter("amount"));
		String date = request.getParameter("date");
		String description = request.getParameter("description");
	//	System.out.println("Amount: " + amount +"\ndate: " + date + "\ndescription: " + description);
//		byte[] imageBytes = (request.getParameter("image")).getBytes();
//		Blob blob = null;
//		
//		try {
//			blob.setBytes(0, imageBytes);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		System.out.println(Service.getUserDatabaseID(Service.currentUser));
		ReimburstmentRequest reimbursement = new ReimburstmentRequest(amount, date, 1, Service.getUserDatabaseID(Service.currentUser), description);
		ReimburstmentRequestDaoImpl rrd = new ReimburstmentRequestDaoImpl();
		System.out.println("USER ID IN REQUEST: " + reimbursement.getUserID());
		rrd.submitReimburstmentRequest(reimbursement);
		response.setStatus(201);
		response.sendRedirect(request.getContextPath() + "/homepage.html");
	}

}
