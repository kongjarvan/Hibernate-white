package site.metacoding.white.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 스프링에서 DB -> rs -> Entity (전략: 디폴트 생성자 호출 한 뒤 setter)
@Getter
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;

	// User 누가 썼는지
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	// Board 어디에 썼는지
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Board board;

	@Builder
	public Comment(Long id, String content, User user, Board board) {
		this.id = id;
		this.content = content;
		this.user = user;
		this.board = board;
		// 양방향 데이터 매핑
		this.board.addComment(this); // 1차 캐시에 있는 board에 comment 추가하기
	}

}
