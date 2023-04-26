package com.library.service.book;

import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;
import java.util.Optional;

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
	
	// 1-2. 책목록보기
	public List<BookRespDto> readAllBook() {

		// 방법 1 : 일반적인 방법
		List<Book> result = bookRepository.findAll();
		
		List<BookRespDto> bookList = new ArrayList<>();
		
		for(Book book : result) {
			bookList.add(new BookRespDto().toDto(book));
		}
		
		return bookList;
	
	/*  
	 *  // 방법 2 : java 8 stream 
		return bookRepository.findAll().stream()  // 1-2-1. bookRepository.findAll().stream() -> 스트림이 하나 만들어지고 findAll로 찾은 List형 Book이 타입 없이 Object 형태로 차례대로 들어온다.(스트림 형태의 데이터)
				.map(new BookRespDto()::toDto)  // 1-2-2. .map을 사용해 타입을 변경해서 return 할 수 있다(필터링 작업). 그대로 return도 가능(기존 인스턴스를 변경한게 아니라 복제해서 타입을 변경해서 return) -> .map으로 원하는 타입의 데이터로 변환됨(map형태의 stream).
				.collect(Collectors.toList());  // 1-2-3. .collect를 이용해 1-4에서 변환한 타입을 List형태로 반환 -> 위에서 변환된 타입을 컬렉션객체로 반환.
	*/
	}
	
	// 1-3. 책 한건보기
	public BookRespDto readBookById(Long bookId) {
		Optional<Book> bookOp = bookRepository.findById(bookId);
		
		if(bookOp.isPresent()) {
			Book findBook = bookOp.get();
			
			return new BookRespDto().toDto(findBook);
		} else {
			throw new RuntimeException("해당 도서가 존재하지 않습니다.");
		}
	}
	
	// 1-4. 책 삭제
	
	// 1-5. 책 수정
}
