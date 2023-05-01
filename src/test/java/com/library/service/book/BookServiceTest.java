package com.library.service.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.library.entity.book.BookRepository;
import com.library.util.mail.MailSenderStub;
import com.library.web.dto.book.BookRespDto;
import com.library.web.dto.book.BookSaveReqDto;

@DataJpaTest  // 3-1. 프로젝트가 실행될때 테스트 환경에서 해당 Repository만 메모리에 올려 실행한다는 뜻.(controller, service는 제외)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 3-2. EmbeddedDatabse를 사용 안함.
public class BookServiceTest {
	
	@Autowired
	private BookRepository bookRepository;

	@Test
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
}
