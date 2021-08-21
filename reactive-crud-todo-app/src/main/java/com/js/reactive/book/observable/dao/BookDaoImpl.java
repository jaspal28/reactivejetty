package com.js.reactive.book.observable.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.js.reactive.book.dto.BookDto;
import com.js.reactive.book.model.BookModel;

import io.reactivex.Observable;

public class BookDaoImpl implements BookDao {

	Map<String, BookModel> BookModels;

	/* singleton implementation */
	private static BookDao instance;

	public static BookDao getInstance() {
		return (instance != null) ? instance : new BookDaoImpl();
	}

	private BookDaoImpl() {
		initializeDB();
	}

	@Override
	public Observable<BookModel> create(BookDto dto) {
		BookModel BookModel = new BookModel(dto.getTitle(), dto.getDescription());
		BookModels.put(BookModel.getId(), BookModel);
		return Observable.fromCallable(() -> BookModel);
	}

	@Override
	public Observable<Optional<BookModel>> read(String id) {
		return Observable.fromCallable(() -> Optional.ofNullable(BookModels.get(id)));
	}

	@Override
	public Observable<List<BookModel>> readAll() {
		return Observable.fromCallable(() -> new ArrayList<>(BookModels.values()));
	}

	@Override
	public Observable<Optional<BookModel>> update(BookModel BookModel) {
		Optional<BookModel> result;
		if (BookModels.get(BookModel.getId()) != null) {
			BookModels.replace(BookModel.getId(), BookModel);
			result = Optional.of(BookModels.get(BookModel.getId()));
		} else {
			result = Optional.empty();
		}
		return Observable.fromCallable(() -> result);
	}

	@Override
	public Observable<Boolean> delete(String id) {
		if (BookModels.get(id) != null) {
			BookModels.remove(id);
			return Observable.fromCallable(() -> true);
		}
		return Observable.fromCallable(() -> false);
	}

	private void initializeDB() {
		BookModels = new HashMap<>();
		BookModel BookModel1 = new BookModel("study reactive", "learn reactive programming");
		BookModels.put(BookModel1.getId(), BookModel1);
		BookModel BookModel2 = new BookModel("learn ReactiveJ", "learn to use ReactiveJ library");
		BookModels.put(BookModel2.getId(), BookModel2);
		BookModel BookModel3 = new BookModel("exercise", "do some exercises");
		BookModels.put(BookModel3.getId(), BookModel3);
	}

	private BookModel updateBookModel(BookModel BookModel, BookDto dto) {
		BookModel.setTitle(dto.getTitle());
		BookModel.setDescription(dto.getDescription());
		return BookModel;
	}

}
