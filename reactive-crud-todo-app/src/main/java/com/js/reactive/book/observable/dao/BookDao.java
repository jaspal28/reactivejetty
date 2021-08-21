package com.js.reactive.book.observable.dao;

import java.util.List;
import java.util.Optional;

import com.js.reactive.book.dto.BookDto;
import com.js.reactive.book.model.BookModel;

import io.reactivex.Observable;

public interface BookDao {
	Observable<BookModel> create(BookDto dto);

	Observable<Optional<BookModel>> read(String id);

	Observable<List<BookModel>> readAll();

	Observable<Optional<BookModel>> update(BookModel toDo);

	Observable<Boolean> delete(String id);

}
