package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // ioc 컨테이너 띄우기
public class BoardRepository {

	private final EntityManager em;

	public void save(Board board) {
		em.persist(board); // insert 시켜줌, 영속화
	}
}
