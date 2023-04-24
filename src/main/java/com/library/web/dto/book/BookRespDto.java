package com.library.web.dto.book;

import com.library.entity.book.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookRespDto {

	private Long id;
	private String title;
	private String author;
	
	public BookRespDto toDto(Book bookPS) {
		this.id = bookPS.getId();
		this.title = bookPS.getTitle();
		this.author = bookPS.getAuthor();
		return this;
	}
}
