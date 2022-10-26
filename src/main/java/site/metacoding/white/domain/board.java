package site.metacoding.white.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 스프링에서 DB -> rs -> Entity (전략: 디폴트 생성자 호출 한 뒤 setter)
@Getter
@Entity // 엔티티로 설정
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
	private Long id; // id에 auto_increment를 설정
	private String title;
	@Column(length = 1000) // varchar의 길이를 설정
	private String content;

	// FK가 만들어짐 user_id
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	// 조회를 위해서만 필요함
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();

	@Builder
	public Board(Long id, String title, String content, User user) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.user = user;
	}

	// 변경하는 코드는 의미 있게 메서드로 구현
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
