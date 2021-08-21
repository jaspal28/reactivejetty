package com.js.reactive.book.nonobservable.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.js.reactive.book.dto.BookDto;
import com.js.reactive.book.model.BookModel;

public class BookDaoImpl implements BookDao {

	Map<String, BookModel> toDos;

	/* singleton implementation */
	private static BookDao instance;

	public static BookDao getInstance() {
		return (instance != null) ? instance : new BookDaoImpl();
	}

	private BookDaoImpl() {
		initializeDB();
	}

	@Override
	public BookModel create(BookDto dto) {
		BookModel todo = new BookModel(dto.getTitle(), dto.getDescription());
		toDos.put(todo.getId(), todo);
		return todo;
	}

	@Override
	public Optional<BookModel> read(String id) {
		return Optional.ofNullable(toDos.get(id));
	}

	@Override
	public List<BookModel> readAll() {
		return toDos.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
	}

	@Override
	public Optional<BookModel> update(BookModel toDo) {
		if (toDos.get(toDo.getId()) != null) {
			toDos.replace(toDo.getId(), toDo);
			return Optional.of(toDos.get(toDo.getId()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean delete(String id) {
		if (toDos.get(id) != null) {
			toDos.remove(id);
			return true;
		}
		return false;
	}

	private void initializeDB() {
		toDos = new HashMap<>();
		BookModel todo1 = new BookModel("study reactive", "learn reactive programming");
		toDos.put(todo1.getId(), todo1);
		BookModel todo2 = new BookModel("learn ReactiveJ", "learn to use ReactiveJ library");
		toDos.put(todo2.getId(), todo2);
		BookModel todo3 = new BookModel("exercise", "do some exercises");
		toDos.put(todo3.getId(), todo3);
	}

}
