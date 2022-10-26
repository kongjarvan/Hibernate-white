package site.metacoding.white.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // ioc 컨테이너 띄우기
public class BoardRepository {

	private final EntityManager em;

	public Board save(Board board) {
		em.persist(board); // insert 시켜줌, update는 안됨
		return board;
	}

	public Optional<Board> findById(Long id) { // optional은 value가 null인지 아닌지 확실하지 않을때 사용
		try {
			Optional<Board> boardOP = Optional.of(
					em.createQuery("select b from Board b where b.id = :id", Board.class)
							.setParameter("id", id)
							.getSingleResult());
			return boardOP;
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public List<Board> findAll() {
		List<Board> boardList = em.createQuery("select b from Board b", Board.class)
				.getResultList();
		return boardList;
	}

	public void deleteById(Long id) {
		em.createQuery("delete from Board b where b.id= :id")
				.setParameter("id", id)
				.executeUpdate();
	}

}
