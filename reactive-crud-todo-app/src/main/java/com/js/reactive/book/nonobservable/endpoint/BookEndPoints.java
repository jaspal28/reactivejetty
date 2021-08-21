package com.js.reactive.book.nonobservable.endpoint;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.js.reactive.app.annotations.Api;
import com.js.reactive.app.utils.Action;
import com.js.reactive.app.utils.EndPoints;
import com.js.reactive.book.dto.BookDto;
import com.js.reactive.book.dto.ResponseDto;
import com.js.reactive.book.model.BookModel;
import com.js.reactive.book.nonobservable.dao.BookDao;
import com.js.reactive.book.nonobservable.dao.BookDaoImpl;

public class BookEndPoints extends EndPoints {

	BookDao bookDao = BookDaoImpl.getInstance();

	// @Api is just a description. It is not going to drive anything.
	@Api(path = "/api/nonobservable/create", method = "POST", consumes = "application/json", produces = "application/json")
	Action createBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		BookDto bookData = (BookDto) getDataFromJsonBodyRequest(request, BookDto.class);
		BookModel bookModel = bookDao.create(bookData);
		ResponseDto responseData = new ResponseDto(200, bookModel);
		toJsonResponse(request, response, responseData);
	};

	@Api(path = "/api/nonobservable/read/{id}", method = "GET", produces = "application/json")
	Action readBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		String id = getPathVariables(request).get("id");
		Optional<BookModel> bookModelOptional = bookDao.read(id);
		if (bookModelOptional.isPresent()) {
			ResponseDto responseData = new ResponseDto(200, bookModelOptional.get());
			toJsonResponse(request, response, responseData);
		} else {
			toJsonResponse(request, response, "Not Found Data for " + id);
		}
	};

	@Api(path = "/api/nonobservable/read", method = "GET", produces = "application/json")
	Action readAllBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		List<BookModel> allBooks = bookDao.readAll();
		if (allBooks == null || allBooks.isEmpty()) {
			toJsonResponse(request, response, "Not Data Found");
		} else {
			toJsonResponse(request, response, allBooks);
		}
	};

	@Api(path = "/api/nonobservable/update", method = "POST", consumes = "application/json", produces = "application/json")
	Action updateBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		BookModel bookData = (BookModel) getDataFromJsonBodyRequest(request, BookModel.class);
		Optional<BookModel> bookModelOptional = bookDao.update(bookData);
		if (bookModelOptional.isPresent()) {
			ResponseDto responseData = new ResponseDto(200, bookModelOptional.get());
			toJsonResponse(request, response, responseData);
		} else {
			toJsonResponse(request, response, "Not Found Data for " + bookData.getId());
		}

	};

	@Api(path = "/api/nonobservable/delete/{id}", method = "GET", produces = "application/json")
	Action deleteBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		String id = getPathVariables(request).get("id");
		Boolean result = bookDao.delete(id);
		if (result) {
			ResponseDto responseData = new ResponseDto(200, "Data deleted successfully");
			toJsonResponse(request, response, responseData);
		} else {
			toJsonResponse(request, response, new ResponseDto(200, "Not Found Data for " + id));
		}
	};

	public BookEndPoints() {
		setEndpoint("/api/nonobservable/create", createBookAction);
		setEndpoint("/api/nonobservable/read/{id}", readBookAction);
		setEndpoint("/api/nonobservable/read", readAllBookAction);
		setEndpoint("/api/nonobservable/update", updateBookAction);
		setEndpoint("/api/nonobservable/delete/{id}", deleteBookAction);
	}

}
