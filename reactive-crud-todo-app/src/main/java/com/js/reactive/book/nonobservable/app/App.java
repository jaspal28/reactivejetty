package com.js.reactive.book.nonobservable.app;

import com.js.reactive.app.jetty.ReactiveJ;
import com.js.reactive.book.nonobservable.endpoint.BookEndPoints;

/**
 * Hello world!
 *
 */

/**
 * http://localhost:8030/api/nonobservable/create
 * 
 * POST Request: { "title":"Text Book", "description":"Text Book testing" }
 * 
 * GET Request with ID :
 * http://localhost:8030/api/nonobservable/read/1e19b56a-0cac-4f43-970e-d0e3c512fb20
 * 
 * Get request for All : http://localhost:8030/api/nonobservable/read
 * 
 * Delete Request : Use GET in post-man
 * http://localhost:8030/api/nonobservable/delete/1e19b56a-0cac-4f43-970e-d0e3c512fb20
 *
 *
 * Update request: Use POST method
 *
 * { "id": "277e12ae-8361-4431-9360-156c386897b2", "title": "Text Book11",
 * "description": "Text Book testing11" }
 * 
 * 
 */
public class App {
	public static void main(String[] args) {
		try {
			new ReactiveJ().port(8030).endpoints(new BookEndPoints()).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
