package com.zevyirmiyahu.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zevyirmiyahu.beans.ReimburstmentRequest;
import com.zevyirmiyahu.daoImpl.ReimburstmentRequestDaoImpl;
import com.zevyirmiyahu.service.Service;

public class RejectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RejectServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int requestId = Integer.parseInt(request.getParameter("reject-id"));
		for (ReimburstmentRequest r : Service.pendingRequests) {
			if (r.getReimburstmentID() == requestId) {
				ReimburstmentRequestDaoImpl rrd = new ReimburstmentRequestDaoImpl();
				r.setStatus("Reject"); // approved it 2
				rrd.resolveRequest(r);
				break; // only can reject one at a time and avoid concurrentModificationException
			}
		}
		response.setStatus(201);
		response.sendRedirect(request.getContextPath() + "/homepage.html");
	}
}
