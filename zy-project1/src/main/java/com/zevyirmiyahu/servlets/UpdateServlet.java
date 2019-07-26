package com.zevyirmiyahu.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zevyirmiyahu.beans.User;
import com.zevyirmiyahu.daoImpl.UserDaoImpl;
import com.zevyirmiyahu.service.Service;

public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getRequestURI().contains("ShowInfoServlet")) {
			System.out.println("ShowInfoServlet");
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(Service.currentUser));
		} else {
			request.getRequestDispatcher("/homepage.html").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		System.out.println("Firstname: " + firstName);
		System.out.println("Lastname: " + lastName.isEmpty());
		if(!firstName.equals("")) Service.currentUser.setFirstName(firstName);
		if(!lastName.equals("")) Service.currentUser.setLastName(lastName);
		if(!address.equals("")) Service.currentUser.setAddress(address);
		if(!phone.equals("")) Service.currentUser.setPhone(phone);
		
		UserDaoImpl udi = new UserDaoImpl();
		udi.updateInformation(Service.currentUser); // update info
		response.setStatus(201);
		response.sendRedirect(request.getContextPath() + "/homepage.html");
	}
}
