package com.js.reactive.book.dto;

import java.util.Date;

public class ResponseDto {
	private int status;
	private Object response;
	private Date datetime;

	public ResponseDto() {
	}

	public ResponseDto(int status, Object response) {
		this.status = status;
		this.response = response;
		this.datetime = new Date();
	}
}
