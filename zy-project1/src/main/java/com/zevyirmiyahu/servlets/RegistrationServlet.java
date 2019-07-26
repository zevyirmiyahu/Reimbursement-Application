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

public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = -4032711958754129937L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		UserDaoImpl udi = new UserDaoImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User(true, request.getParameter("first-name"), request.getParameter("last-name"), 
				request.getParameter("address"), request.getParameter("phone"),
				request.getParameter("email"), request.getParameter("password"));
		// check if valid registration
		if(verifyRegistration(user, request)) {			
			UserDaoImpl udi = new UserDaoImpl();
			udi.createUser(user);
			response.setStatus(201);
			response.sendRedirect(request.getContextPath() + "/homepage.html");
		}
		else {
			response.setStatus(201);
			System.out.println("Registration not successful");
		}
	}
	
	private boolean verifyRegistration(User user, HttpServletRequest request) {
		Service.update();
		for(User existingUser : Service.users) {
			if(existingUser.getEmail().equals(user.getEmail())) {
				return false;
			}
		}
		return true;
	}
}
