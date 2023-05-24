package com.library.web.api.book;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.library.web.dto.book.BookSaveReqDto;

// 통합테스트 (C, S, R)
// 컨트롤러만 테스트하는게 아니라 통합적으로 테스트
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)  // 1-1. @SpringBootTest 어노테이션을 붙이면 통합 테스트가 된다.
public class BookApiControllerTest {

	@Autowired
	private TestRestTemplate rt;
	
	private static ObjectMapper om;  // 1-2.
	
	private static HttpHeaders headers;
	
	@BeforeAll
	public static void init() {  // 1-3. static 변수란 main 메소드가 실행 되기 전에 jvm이 시작될 때 최초에 1번만 메모리에 띄운다는 뜻.
		om = new ObjectMapper();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
/*	@Test
	public void DI_test() {
		if(bookService == null) {
			System.out.println("null이에요");
		} else {
			System.out.println("null이 아니에요.");
		}
	}
*/	
	@Test
	public void saveBook_test() throws Exception {
		// given
		BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
		bookSaveReqDto.setTitle("스프링2강");
		bookSaveReqDto.setAuthor("겟인데어");
		
//		ObjectMapper om = new ObjectMapper();
		
		// 1-4. json 형태의 데이터로 변환
		String body = om.writeValueAsString(bookSaveReqDto);
		
		// when
		// 1-5. request에 json body 데이터랑 header 셋팅.
		HttpEntity<String> request = new HttpEntity<>(body, headers);
		
		ResponseEntity<String> response = rt.exchange("/api/books", HttpMethod.POST, request, String.class);
		
		// then
		DocumentContext dc = JsonPath.parse(response.getBody());
	
//		System.out.println(dc.jsonString());
		
		String title = dc.read("$.data.title");
		String author = dc.read("$.data.author");
		
		assertThat(title).isEqualTo(bookSaveReqDto.getTitle());
		assertThat(author).isEqualTo(bookSaveReqDto.getAuthor());
		
	}
}
