package site.metacoding.white.domain;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository // ioc 컨테이너 띄우기
public class UserRepository {

	private final EntityManager em;

	public User save(User user) {

		// persistence Context에 영속화 시키기 -> 자동 flush (트랜젝션 종료시)
		log.debug("디버그: " + user.getId());
		em.persist(user); // insert 시켜줌, update는 안됨
		log.debug("디버그: " + user.getId());
		return user;
	}

	public User findByUsername(String username) {
		User principal = em.createQuery("select u from User u where u.username= :username", User.class)
				.setParameter("username", username)
				.getSingleResult();
		return principal;
	}

	public Optional<User> findById(Long id) {
		try {
			Optional<User> userOP = Optional.of(
					em.createQuery("select u from User u where u.id = :id",
							User.class)
							.setParameter("id", id)
							.getSingleResult());
			return userOP;
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}
