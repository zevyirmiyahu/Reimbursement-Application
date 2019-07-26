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

/*
 * Servlet responsible for retrieving PERSONAL reimbursement information from database
 */
public class RequestInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RequestInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ReimburstmentRequest> allRequests = new ArrayList<>();
		allRequests.addAll(getPersonalRequests(Service.pendingRequests));
		allRequests.addAll(getPersonalRequests(Service.resolvedRequests));
		if (request.getRequestURI().contains("RequestInfoServlet")) {
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(allRequests));
		} else {
			request.getRequestDispatcher("/homepage.html").forward(request, response);
		}
	}
	
	private List<ReimburstmentRequest> getPersonalRequests(List<ReimburstmentRequest> list) {
		int userId = Service.getUserDatabaseID(Service.currentUser);
		List<ReimburstmentRequest> personalRequests = new ArrayList<>();
		for(ReimburstmentRequest r : list) {
			if(r.getUserID() == userId) personalRequests.add(r);
		}
		return personalRequests;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
