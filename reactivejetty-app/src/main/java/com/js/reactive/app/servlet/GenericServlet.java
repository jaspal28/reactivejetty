package com.js.reactive.app.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.js.reactive.app.utils.RestHandler;

public class GenericServlet extends HttpServlet {

	private final static Logger log = Logger.getLogger(GenericServlet.class.getName());

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.startAsync();
		log.info(LocalDateTime.now() + " - " + this.getClass().getSimpleName()
				+ " - start Async context from http request: " + request.getRequestURI());
		try {
			RestHandler.getInstance().getEndpointIfMatches(request.getRequestURI()).act(request, response);
		} catch (Exception e) {
			log.info(LocalTime.now() + ": Internal Server Error (code 500) " + e.toString());
			request.setAttribute("internal-server-error", e.toString());
			RestHandler.getInstance().getEndpointIfMatches("/internal-server-error").act(request, response);
		}
	}

}
