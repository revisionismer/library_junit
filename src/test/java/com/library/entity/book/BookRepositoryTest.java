package com.library.entity.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest  // 2-1. 프로젝트가 실행될때 테스트 환경에서 해당 Repository만 메모리에 올려 실행한다는 뜻.(controller, service는 제외)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 2-2. EmbeddedDatabse를 사용 안함.
public class BookRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	// 1-1. 책 등록 
	@Test
	public void 책등록_test() {
		System.out.println("책등록_test 실행");
		
		// given (데이터 준비)
		String title = "junit5";
		String author = "메타코딩";
		
		Book book = Book.builder()
				.title(title)
				.author(author)
				.build();
		
		// when (테스트 실행)
		Book bookPS = bookRepository.save(book);
		
		// then (검증)
		assertEquals(title, bookPS.getTitle());
		assertEquals(author, bookPS.getAuthor());
	}
	
	// 1-2. 책 목록보기
	@Test
	public void 책목록보기_test() {
		System.out.println("책목록보기_test 실행");
	}
	
	// 1-3. 책 한건보기
	@Test
	public void 책한건보기_test() {
		System.out.println("책한건보기_test 실행");
	}
	
	// 1-4. 책 수정
	@Test
	public void 책수정_test() {
		System.out.println("책수정_test 실행");
	}
	
	// 1-5. 책 삭제
	@Test
	public void 책삭제_test() {
		System.out.println("책삭제_test 실행");
	}
}
