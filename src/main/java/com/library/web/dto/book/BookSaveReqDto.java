package com.library.web.dto.book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.library.entity.book.Book;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter  // 1-1. Controller에서 Setter가 호출되면서 Dto에 값이 채워진다.
public class BookSaveReqDto {

	@Size(min = 1, max = 50)  // 1-4.
	@NotBlank  // 1-2. 유효성 검사 not null, not blank
	private String title;
	
	@Size(min = 2, max = 20) // 1-5.
	@NotBlank // 1-3.
	private String author;
	
	public Book toEntity() {
		return Book.builder()
				.title(title)
				.author(author)
				.build();
	}
}
