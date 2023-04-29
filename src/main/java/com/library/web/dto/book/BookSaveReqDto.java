package com.library.web.dto.book;

import com.library.entity.book.Book;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter  // 1-1. Controller에서 Setter가 호출되면서 Dto에 값이 채워진다.
public class BookSaveReqDto {

	private String title;
	private String author;
	
	public Book toEntity() {
		return Book.builder()
				.title(title)
				.author(author)
				.build();
	}
}
