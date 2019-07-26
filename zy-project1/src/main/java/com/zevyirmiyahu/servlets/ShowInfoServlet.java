package com.zevyirmiyahu.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.zevyirmiyahu.service.Service;

public class ShowInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShowInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String id = "" + Service.currentUser.getUserID();
		String firstName = Service.currentUser.getFirstName();
		String lastName = Service.currentUser.getLastName();
		String address = Service.currentUser.getAddress();
		String email = Service.currentUser.getEmail();
		String phone = Service.currentUser.getPhone();
		String isManager = "" + Service.currentUser.getIsManager();
		List<String> info = new ArrayList<>();
		info.add(firstName);
		info.add(lastName);
		info.add(address);
		info.add(email);
		info.add(phone);
		info.add(isManager);
		
		if (request.getRequestURI().contains("ShowInfoServlet")) {
			System.out.println("ShowInfoServlet");
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(info));
		} else {
			request.getRequestDispatcher("/homepage.html").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
