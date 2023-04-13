package com.library.entity.book;

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
	}
	
	// 1-2. 책 목록보기
	@Test
	public void 책목록보기_test() {
		
	}
	
	// 1-3. 책 한건보기
	@Test
	public void 책한건보기_test() {
		
	}
	
	// 1-4. 책 수정
	@Test
	public void 책수정_test() {
		
	}
	
	// 1-5. 책 삭제
	@Test
	public void 책삭제_test() {
		
	}
}
