package com.library.web.api.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.service.book.BookService;
import com.library.web.dto.CMRespDto;
import com.library.web.dto.book.BookRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookApiController {

	private final BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<?> findAllBook() {
		return new ResponseEntity<>(new CMRespDto<>(1, "도서 리스트 불러오기", bookService.readAllBook()), HttpStatus.OK);
	}
	
	@GetMapping("/books/{bookId}")
	public ResponseEntity<?> findBookById(@PathVariable("bookId") Long bookId) {
		
		BookRespDto dto = bookService.readBookById(bookId);
		
		return new ResponseEntity<>(new CMRespDto<>(1, bookId + "번 도서 불러오기", dto), HttpStatus.OK);
	}
}
