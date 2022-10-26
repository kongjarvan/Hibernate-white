package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CommentRepository {
	private final EntityManager em;

	public Comment save(Comment comment) {
		em.persist(comment); // insert 시켜줌, update는 안됨
		return comment;
	}

	public void deleteById(Long id) {
		em.createQuery("delete from Comment c where c.id= :id")
				.setParameter("id", id)
				.executeUpdate();
	}

}
