package com.library.web.api.book;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.service.book.BookService;
import com.library.web.dto.CMRespDto;
import com.library.web.dto.book.BookRespDto;
import com.library.web.dto.book.BookSaveReqDto;
import com.library.web.handler.exception.api.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookApiController {

	private final BookService bookService;
	
	// 1-1. 책 등록
	@PostMapping("/books")
	public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			// 2023-05-17 -> api exception 처리 완료 
			throw new CustomValidationApiException("도서 저장 실패", errorMap);
		}
		
		BookRespDto result = bookService.registerBook(bookSaveReqDto);
		
		return new ResponseEntity<>(new CMRespDto<>(1, result.getId() + "번 도서 저장 성공", result), HttpStatus.CREATED);
	}
	
	// 1-2. 책 목록보기
	@GetMapping("/books")
	public ResponseEntity<?> findAllBook() {
		return new ResponseEntity<>(new CMRespDto<>(1, "도서 리스트 불러오기", bookService.readAllBook()), HttpStatus.OK);
	}
	
	// 1-3. 책 한건 보기
	@GetMapping("/books/{bookId}")
	public ResponseEntity<?> findBookById(@PathVariable("bookId") Long bookId) {
		
		BookRespDto dto = bookService.readBookById(bookId);
		
		return new ResponseEntity<>(new CMRespDto<>(1, bookId + "번 도서 불러오기", dto), HttpStatus.OK);
	}
	
	// 1-4. 책 삭제하기
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?> deleteBookById(@PathVariable("bookId") Long bookId) {
		return new ResponseEntity<>(new CMRespDto<>(1, bookId +"도서 삭제하기", null), HttpStatus.OK);
	}
	
	// 1-5. 책 수정하기
	@PutMapping("/books/{bookId}")
	public ResponseEntity<?> updateBook() {
		return new ResponseEntity<>(new CMRespDto<>(1, "도서 수정하기", null), HttpStatus.OK);
	}
}
