package com.library.service.book;

import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any; // 4-5. static org.mockito.ArgumentMatchers.any
import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.library.entity.book.Book;
import com.library.entity.book.BookRepository;
import com.library.util.mail.MailSender;
import com.library.web.dto.book.BookRespDto;
import com.library.web.dto.book.BookSaveReqDto;

@ExtendWith(MockitoExtension.class)  // 4-1. 가짜 메모리 환경 만들기
// @DataJpaTest  // 3-1. 프로젝트가 실행될때 테스트 환경에서 해당 Repository만 메모리에 올려 실행한다는 뜻.(controller, service는 제외)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 3-2. EmbeddedDatabse를 사용 안함.
public class BookServiceTest {
	
//	@Autowired
//	private BookRepository bookRepository;
	
	@InjectMocks  // 4-4. BookService를 메모리(heap)에 올린다. heap에 올라온 BookService에 있는 bookRepository와 mailSender에 가짜 객체가 주입된다.
	private BookService bookService;
	
	@Mock  // 4-2. 가짜 환경에 올린다.
	private BookRepository bookRepository;
	
	@Mock  // 4-3. 가짜 환경에 올린다.
	private MailSender mailSender;

/*	@Test
	public void 책등록하기_테스트() {
		// given
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("junit 강의");
		dto.setAuthor("메타코딩");
		
		// stub
		MailSenderStub mailSenderStub = new MailSenderStub();
		
		// when
		BookService bookService = new BookService(bookRepository, mailSenderStub);
		BookRespDto bookRespDto = bookService.registerBook(dto);
		
		// then
		assertEquals("스프링 강의", bookRespDto.getTitle());
		assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
	}
*/
	
	@Test
	public void 책등록하기_테스트_Mock() {
		// given
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("junit 강의");
		dto.setAuthor("메타코딩");
		
		// stub : 4-6. 가짜 환경 세팅
		when(bookRepository.save(any())).thenReturn(dto.toEntity());
		when(mailSender.send()).thenReturn(true);
		
		// when
		BookRespDto bookRespDto = bookService.registerBook(dto);
		
		// then
//		assertEquals(dto.getTitle(), bookRespDto.getTitle());
//		assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
	
		// 4-7. assertJ : aserrtThat 이용
		assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
		assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
	}	
	
	
	@Test
	public void 책목록보기_테스트() {
		// given(파라미터로 들어올 데이터)
		
		// stub(가설)
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "junit강의", "메타코딩"));
		books.add(new Book(2L, "스프링 강의", "겟인데어"));
		
		when(bookRepository.findAll()).thenReturn(books);
		
		// when(실제로 가져올 데이터)
		List<BookRespDto> result = bookService.readAllBook();
		
		for(BookRespDto dto : result) {
			System.out.println(dto.getId());
			System.out.println(dto.getTitle());
			System.out.println(dto.getAuthor());
		}
		
		// then(검증)
		assertThat(result.get(0).getTitle()).isEqualTo(books.get(0).getTitle());
		assertThat(result.get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
	}
	
	@Test
	public void 책한건보기_테스트_Mock() {
		// given : 6-1. 테스트에 사용할 데이터 준비
		Long id = 1L;
		Book book = new Book(1L, "junit 강의", "메타코딩");
		Optional<Book> bookOp = Optional.of(book);
		
		// stub(가설) : 6-2. 가짜 환경에 데이터 셋팅
		when(bookRepository.findById(id)).thenReturn(bookOp);
		
		// when(실제로 가져올 데이터) : 6-3. 가짜 환경에 올라온 데이터를 가져온다.
		BookRespDto result = bookService.readBookById(id);
		
		// then(검증) : 6-4. assertJ : assertThat 이용
		assertThat(result.getTitle()).isEqualTo(book.getTitle());
		assertThat(result.getAuthor()).isEqualTo(book.getAuthor());
	}
	
	@Test
	public void 책수정하기_테스트_Mock() {
		// given : 7-1. 
		Long id = 1L;
		Book book = new Book(1L, "junit 강의", "메타코딩");
		Optional<Book> bookOp = Optional.of(book);
		
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("포토그램");
		dto.setAuthor("겟인데어");
		
		// stub(가설) : 7-2. 가짜  환경에 데이터 셋팅
		when(bookRepository.findById(id)).thenReturn(bookOp);
		
		// when(실제로 가져올 데이터) : 7-3. 가짜 환경에 올라와 있는 데이터를 수정한다.
		bookService.updateBook(id, dto);
		BookRespDto findBook = bookService.readBookById(id);
		
		// then : 7-4. 검증
		assertThat(findBook.getTitle()).isEqualTo(dto.getTitle());
		assertThat(findBook.getAuthor()).isEqualTo(dto.getAuthor());
	}
	
	// 참고 : 책 삭제하기 테스트는 생략
}
