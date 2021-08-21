package com.js.reactive.book.dto;

import java.util.Objects;

public class BookDto {
	private String title;
	private String description;

	public BookDto() {
	}

	public BookDto(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BookDto))
			return false;
		BookDto toDoDto = (BookDto) o;
		return Objects.equals(getTitle(), toDoDto.getTitle())
				&& Objects.equals(getDescription(), toDoDto.getDescription());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTitle(), getDescription());
	}
}
