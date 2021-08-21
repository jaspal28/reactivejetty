package com.js.reactive.book.nonobservable.dao;

import java.util.List;
import java.util.Optional;

import com.js.reactive.book.dto.BookDto;
import com.js.reactive.book.model.BookModel;

public interface BookDao {
	BookModel create(BookDto dto);

	Optional<BookModel> read(String id);

	List<BookModel> readAll();

	Optional<BookModel> update(BookModel toDo);

	Boolean delete(String id);
}
