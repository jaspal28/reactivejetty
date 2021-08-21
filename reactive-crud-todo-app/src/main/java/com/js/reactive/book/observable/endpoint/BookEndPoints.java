package com.js.reactive.book.observable.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.js.reactive.app.annotations.Api;
import com.js.reactive.app.utils.Action;
import com.js.reactive.app.utils.EndPoints;
import com.js.reactive.book.dto.BookDto;
import com.js.reactive.book.dto.ResponseDto;
import com.js.reactive.book.model.BookModel;
import com.js.reactive.book.observable.dao.BookDao;
import com.js.reactive.book.observable.dao.BookDaoImpl;

import io.reactivex.Observable;

/**
 * If we are transforming one observable to another observable and operator
 * inside returns an observable (like bookDao.create(input)), we are going to
 * have observable of observable. Use flatMap in this case.
 * 
 * @author demo
 *
 */
public class BookEndPoints extends EndPoints {
	BookDao bookDao = BookDaoImpl.getInstance();

	// @Api is just a description. It is not going to drive anything.
	@Api(path = "/api/observable/create", method = "POST", consumes = "application/json", produces = "application/json")
	Action createBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request).map(req -> (BookDto) getDataFromJsonBodyRequest(req, BookDto.class)) // returns
																										// observable
				.flatMap(input -> bookDao.create(input)) // used flatMap because we get Observable of Observable
				.subscribe(output -> toJsonResponse(request, response, new ResponseDto(200, output)));
	};

	@Api(path = "/api/observable/read/{id}", method = "GET", produces = "application/json")
	Action readBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request).map(req -> getPathVariables(req).get("id")).flatMap(id -> bookDao.read(id))
				.subscribe(output -> toJsonResponse(request, response,
						new ResponseDto(200, output.isPresent() ? output.get() : "Not Found Data")));
		/*
		 * String id = getPathVariables(request).get("id"); Optional<BookModel>
		 * bookModelOptional = bookDao.read(id); if (bookModelOptional.isPresent()) {
		 * ResponseDto responseData = new ResponseDto(200, bookModelOptional.get());
		 * toJsonResponse(request, response, responseData); } else {
		 * toJsonResponse(request, response, "Not Found Data for " + id); }
		 */
	};

	@Api(path = "/api/observable/read", method = "GET", produces = "application/json")
	Action readAllBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request).flatMap(req -> bookDao.readAll()).subscribe(
				output -> toJsonResponse(request, response, new ResponseDto(200, output)),
				error -> toJsonResponse(request, response, new ResponseDto(200, "No data found")));
		/*
		 * List<BookModel> allBooks = bookDao.readAll(); if (allBooks == null ||
		 * allBooks.isEmpty()) { toJsonResponse(request, response, "Not Data Found"); }
		 * else { toJsonResponse(request, response, allBooks); }
		 */
	};

	@Api(path = "/api/observable/update", method = "POST", consumes = "application/json", produces = "application/json")
	Action updateBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request).map(req -> (BookModel) getDataFromJsonBodyRequest(req, BookModel.class))
				.flatMap(input -> bookDao.update(input)) // used flatMap because we get Observable of Observable
				.subscribe(output -> toJsonResponse(request, response,
						new ResponseDto(200, output.isPresent() ? output.get() : "No Data Found")));
		/*
		 * BookModel bookData = (BookModel) getDataFromJsonBodyRequest(request,
		 * BookModel.class); Optional<BookModel> bookModelOptional =
		 * bookDao.update(bookData); if (bookModelOptional.isPresent()) { ResponseDto
		 * responseData = new ResponseDto(200, bookModelOptional.get());
		 * toJsonResponse(request, response, responseData); } else {
		 * toJsonResponse(request, response, "Not Found Data for " + bookData.getId());
		 * }
		 */

	};

	@Api(path = "/api/observable/delete/{id}", method = "GET", produces = "application/json")
	Action deleteBookAction = (HttpServletRequest request, HttpServletResponse response) -> {
		Observable.just(request).map(req -> getPathVariables(req).get("id")).flatMap(id -> bookDao.delete(id))
				.subscribe(found -> toJsonResponse(request, response,
						new ResponseDto(200, found ? "Data deleted Successfully" : "Not Found Data")));
		/*
		 * String id = getPathVariables(request).get("id"); Boolean result =
		 * bookDao.delete(id); if (result) { ResponseDto responseData = new
		 * ResponseDto(200, "Data deleted successfully"); toJsonResponse(request,
		 * response, responseData); } else { toJsonResponse(request, response, new
		 * ResponseDto(200, "Not Found Data for " + id)); }
		 */
	};

	public BookEndPoints() {
		setEndpoint("/api/observable/create", createBookAction);
		setEndpoint("/api/observable/read/{id}", readBookAction);
		setEndpoint("/api/observable/read", readAllBookAction);
		setEndpoint("/api/observable/update", updateBookAction);
		setEndpoint("/api/observable/delete/{id}", deleteBookAction);
	}

}
