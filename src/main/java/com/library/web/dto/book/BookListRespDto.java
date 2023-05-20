package com.library.web.dto.book;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor
public class BookListRespDto {

	List<BookRespDto> items;
	
	@Builder
	public BookListRespDto(List<BookRespDto> bookList) {
		this.items = bookList;
	}
}
