package com.library.entity.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest  // 2-1. 프로젝트가 실행될때 테스트 환경에서 해당 Repository만 메모리에 올려 실행한다는 뜻.(controller, service는 제외)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 2-2. EmbeddedDatabse를 사용 안함.
public class BookRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	
//	@BeforeAll // 2-2. 테스트 시작 전에 한번만 실행
	@BeforeEach  // 2-3. 각 테스트 시작전에 한번씩 실행
	public void prepare_data() {
		for(int i = 0; i < 3; i++) {
			// given (데이터 준비)
			String title = "junit5_" + i;
			String author = "메타코딩_" + i;
			
			Book book = Book.builder()
					.title(title)
					.author(author)
					.build();
			
			// when (테스트 실행)
			bookRepository.save(book);
		}
		
		System.out.println("==========================================");
		
		List<Book> booksPS = bookRepository.findAll();
		for(Book book : booksPS) {
			System.out.println(book.toString());
		}
		
		System.out.println("==========================================");
		
		
	}
	
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
		// given (데이터 준비)
		for(int i = 0; i < 3; i++) {
			String title = "junit5_" + i;
			String author = "메타코딩_" + i;
			
			Book book = Book.builder()
					.title(title)
					.author(author)
					.build();
			
			bookRepository.save(book);
		}
		// when (테스트 실행)
		List<Book> result = bookRepository.findAll();
		
		// then (검증)
		for(Book book : result) {
			System.out.println(book.getId());
			System.out.println(book.getTitle());
			System.out.println(book.getAuthor());
		}
		
	}
	
	// 1-3. 책 한건보기
	@Test
	public void 책한건보기_test() {
		System.out.println("책한건보기_test 실행");
		
		// given (데이터 준비)
		for(int i = 0; i < 3; i++) {
			// given (데이터 준비)
			String title = "junit5_" + i;
			String author = "메타코딩_" + i;
			
			Book book = Book.builder()
					.title(title)
					.author(author)
					.build();
			
			// when (테스트 실행)
			bookRepository.save(book);
		}
			
		// when
		Optional<Book> bookOp = bookRepository.findById(1L);
		
		// then
		if(bookOp.isPresent()) {
			Book bookPS = bookOp.get();
			
			assertEquals("junit5_" + 0, bookPS.getTitle());
			assertEquals("메타코딩_" + 0, bookPS.getAuthor());	
		}
	
	}
	
	// 1-4. 책 수정
	@Test
	public void 책수정_test() {
		System.out.println("책수정_test 실행");
		// given
		
		// when
		Optional<Book> bookOp = bookRepository.findById(1L);
		
		// then
		if(bookOp.isPresent()) {
			Book bookPS = bookOp.get();
			
			Book modifiedBook = Book.builder()
							.id(bookPS.getId())
							.title("수정했어요")
							.author("수정자")
							.build();
			
			bookRepository.save(modifiedBook);
			
			Book findBook = bookOp.get();
			
			assertEquals("junit5_0", bookPS.getTitle());
			assertEquals("메타코딩_0", bookPS.getAuthor());
			
			assertEquals("수정했어요", findBook.getTitle());
			assertEquals("수정자", findBook.getAuthor());	
		}		
		
	}
	
	// 1-5. 책 삭제
	@Test
	public void 책삭제_test() {
		System.out.println("책삭제_test 실행");
		
		for(int i = 0; i < 3; i++) {
			String title = "junit5_" + i;
			String author = "메타코딩_" + i;
			
			Book book = Book.builder()
					.title(title)
					.author(author)
					.build();
			
			bookRepository.save(book);
		}
		
		Optional<Book> findBook = bookRepository.findById(1L);
		
		if(findBook.isPresent()) {
			Book book = findBook.get();
		
			bookRepository.delete(book);
		}
		
		Optional<Book> book = bookRepository.findById(1L);
		
		if(!book.isPresent()) {
			System.out.println("삭제되었습니다.");
		}
	}
}
