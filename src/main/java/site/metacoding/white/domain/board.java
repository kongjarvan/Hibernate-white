package site.metacoding.white.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity // 엔티티로 설정
public class board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
	private Long id; // id에 auto_increment를 설정
	private String title;
	@Column(length = 1000) // varchar의 길이를 설정
	private String content;
	private String temp;
	// private Timestamp createdAt;
}
