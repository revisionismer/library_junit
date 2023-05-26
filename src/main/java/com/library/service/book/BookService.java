package com.library.service.book;

// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.entity.book.Book;
import com.library.entity.book.BookRepository;
import com.library.util.mail.MailSender;
import com.library.web.dto.book.BookListRespDto;
import com.library.web.dto.book.BookRespDto;
import com.library.web.dto.book.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final MailSender mailSender;
	
	// 1-1. 책 등록
	@Transactional(rollbackFor = RuntimeException.class)
	public BookRespDto registerBook(BookSaveReqDto dto) {	
		Book bookPS = bookRepository.save(dto.toEntity());
		
		if(bookPS != null) {
			if(!mailSender.send()) {
				throw new RuntimeException("메일이 전송되지 않았습니다.");
			}
		}
		
		return new BookRespDto().toDto(bookPS);
	}
	
	// 1-2. 책목록보기
//	public List<BookRespDto> readAllBook() {
	public BookListRespDto readAllBook() {

		/* 방법 1 : 일반적인 방법
		List<Book> result = bookRepository.findAll();
			
		List<BookRespDto> bookList = new ArrayList<>();
			
		for(Book book : result) {
			bookList.add(new BookRespDto().toDto(book));
		}
			
		return bookList;
			
		*/
		
		/* 방법 2 : java 8 stream 1 : BookRespDto를 new해서 dto return
		return bookRepository.findAll().stream()  // 1-2-1. bookRepository.findAll().stream() -> 스트림이 하나 만들어지고 findAll로 찾은 List형 Book이 타입 없이 Object 형태로 차례대로 들어온다.(스트림 형태의 데이터)
				.map(new BookRespDto()::toDto)  // 1-2-2. .map을 사용해 타입을 변경해서 return 할 수 있다(필터링 작업). 그대로 return도 가능(기존 인스턴스를 변경한게 아니라 복제해서 타입을 변경해서 return) -> .map으로 원하는 타입의 데이터로 변환됨(map형태의 stream). -> :: -> 메소드 참조 문법
				.collect(Collectors.toList());  // 1-2-3. .collect를 이용해 1-4에서 변환한 타입을 List형태로 반환 -> 위에서 변환된 타입을 컬렉션객체로 반환.
		*/
			
		/* 방법 3 : java 8 stream 2 : lombok builder 패턴 이용해서 book 엔티티를 사용해 bookRespDto return
		return bookRepository.findAll().stream()
				.map(Book::toDto)
				.collect(Collectors.toList());
		*/
			
		/* 방법 4 : java 8 stream 3 : 방법 3번을 람다식 형태로 변형
		return bookRepository.findAll().stream()
				.map( (bookPS) -> bookPS.toDto() )
				.collect(Collectors.toList());	
		*/
		/* 방법 5 : java 8 stream 4 : return 타입 통일(컬렉션 객체만 사용 했을 경우 문제점 해결) */
		List<BookRespDto> dtos = bookRepository.findAll().stream()
				.map( (bookPS) -> bookPS.toDto() )
				.collect(Collectors.toList());
	
		// 2023-05-19 -> 컬렉션 객체로 return시 발생할 수 있는 문제점 해결(data 응답시 list형태의 컬렉션 객체로 return하는건 좋은 방법이 아니다)
//		BookListRespDto bookListRespDto = BookListRespDto.builder().bookList(dtos).build();
	
		BookListRespDto bookListRespDto = new BookListRespDto(dtos);
		
		return bookListRespDto;	
	}
	
	// 1-3. 책 한건보기
	public BookRespDto readBookByBookId(Long bookId) {
		Optional<Book> bookOp = bookRepository.findById(bookId);
		
		if(bookOp.isPresent()) {
			Book findBook = bookOp.get();
			return new BookRespDto().toDto(findBook);
		} else {
			throw new RuntimeException("해당 도서가 존재하지 않습니다.");
		}
	}
	
	// 1-4. 책 삭제
	@Transactional(rollbackFor = RuntimeException.class)
	public void deleteBookById(Long bookId) {
		// 1-4-1. id가 실제 db에 없는 값이여도 실행은되는데 id값이 만약 null이면 illegalArgumentException 터진다.
		try {
			bookRepository.deleteById(bookId);
		} catch (Exception e) {
			throw new RuntimeException("id값이 null입니다.");
		}
	}
	
	// 1-5. 책 수정
	@Transactional(rollbackFor = RuntimeException.class)
	public BookRespDto updateBook(Long bookId, BookSaveReqDto dto) {
		Optional<Book> bookOp = bookRepository.findById(bookId);
		
		if(bookOp.isPresent()) {
			Book bookPS = bookOp.get();
			
			// 1-5-1. 변경 감지
			bookPS.update(dto.getTitle(), dto.getAuthor());
			
			return bookPS.toDto();
		} else {
			throw new RuntimeException("해당 id를 찾을 수 없습니다.");
		}
	}
}
