package site.metacoding.white.domain;

import java.util.Optional;

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

	public Optional<Comment> findById(Long id) { // optional은 value가 null인지 아닌지 확실하지 않을때 사용
		try {
			Optional<Comment> commentOP = Optional.of(em.createQuery("select c from Comment c where c.id = :id",
					Comment.class)
					.setParameter("id", id)
					.getSingleResult());
			return commentOP;
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public void deleteById(Long id) {
		em.createQuery("delete from Comment c where c.id= :id")
				.setParameter("id", id)
				.executeUpdate();
	}

}
