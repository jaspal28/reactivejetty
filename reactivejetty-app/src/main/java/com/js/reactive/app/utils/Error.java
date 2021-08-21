package com.js.reactive.app.utils;

import java.time.LocalDateTime;

/**
 * This pojo class encapsulates the error which will be sent in output in the
 * case of failure
 * 
 * @author demo
 *
 */
public class Error {
	private int code;
	private String path;
	private String description;
	private String dateTime;

	public Error() {
	}

	public Error(int code, String path, String description) {
		this.code = code;
		this.path = path;
		this.description = description;
		this.dateTime = LocalDateTime.now().toString();
	}
}