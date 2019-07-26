package com.zevyirmiyahu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zevyirmiyahu.beans.User;
import com.zevyirmiyahu.service.Service;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/failedlogin.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service.update(); // used to keep lists consistent with database
		System.out.println("email: " + request.getParameter("email"));
		System.out.println("password: " + request.getParameter("password"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = findUser(email, password);
		if(user != null) {
			if(user.getIsManager()) {				
				System.out.println("Verified Manager Login");
				Service.currentUser = user;
				response.setStatus(201);
				response.sendRedirect(request.getContextPath() + "/homepage.html");
			}
			else {
				System.out.println("Verified Employee Login");
				Service.currentUser = user;
				response.setStatus(201);
				response.sendRedirect(request.getContextPath() + "/homepage.html");
			}
		}
		else {
			System.out.println("Unverified Login");
			response.setStatus(201);
			doGet(request, response);
		}
	}
	
	private User findUser(String email, String password) {
		for(User user : Service.users) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
}
