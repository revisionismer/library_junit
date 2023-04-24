package com.library.service.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.entity.book.Book;
import com.library.entity.book.BookRepository;
import com.library.web.dto.book.BookRespDto;
import com.library.web.dto.book.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	
	// 1-1. 책 등록
	@Transactional(rollbackFor = RuntimeException.class)
	public BookRespDto registerBook(BookSaveReqDto dto) {
		Book bookPS = bookRepository.save(dto.toEntity());
		return new BookRespDto().toDto(bookPS);
	}
	// 1-2. 책 목록보기
	
	// 1-3. 책 한건보기
	
	// 1-4. 책 삭제
	
	// 1-5. 책 수정
}
