package com.library.entity.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 1-2. 기본키는 DB에 맡게 설정.
	private Long id;
	
	@Column(length = 50, nullable = false)  // 1-3. title의 길이는 50자, null 값 허용 X
	private String title;
	
	@Column(length = 20, nullable = false)  // 1-4. author의 길이는 20자, null 값 허용 X
	private String author;
	
	// 1-1. Builder 패턴을 사용해서 엔티티를 만들기 위해 어노테이션 부착.
	@Builder
	public Book(Long id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}
	
	public void update(String title, String author) {
		this.title = title;
		this.author = author;
	}
}
