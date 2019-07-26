package com.zevyirmiyahu.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.service.Service;

// Returns ALL employee requests EXCEPT those that are from current manager
public class AllRequestInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AllRequestInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ReimburstmentRequest> allRequests = new ArrayList<>();
		allRequests.addAll(removeCurrentUserRequests(Service.pendingRequests));
		allRequests.addAll(removeCurrentUserRequests(Service.resolvedRequests));
		if (request.getRequestURI().contains("AllRequestInfoServlet")) {
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(allRequests));
		} else {
			request.getRequestDispatcher("/homepage.html").forward(request, response);
		}
	}

	private List<ReimburstmentRequest> removeCurrentUserRequests(List<ReimburstmentRequest> list) {
		List<ReimburstmentRequest> nonPersonalRequests = new ArrayList<>();
		int userId = Service.getUserDatabaseID(Service.currentUser);
		for(ReimburstmentRequest r : list) {
			if(r.getUserID() != userId) nonPersonalRequests.add(r);
		}
		return nonPersonalRequests;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
