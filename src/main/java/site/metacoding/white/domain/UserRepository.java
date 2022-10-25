package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // ioc 컨테이너 띄우기
public class UserRepository {

	private final EntityManager em;

	public User save(User user) {
		System.out.println("ccc: " + user.getId()); // 영속화 전
		// persistence Context에 영속화 시키기 -> 자동 flush (트랜젝션 종료시)
		em.persist(user); // insert 시켜줌, update는 안됨
		System.out.println("ccc: " + user.getId()); // 영속화 후 (DB와 동기화)
		return user;
	}

	public User findByUsername(String username) {
		User principal = em.createQuery("select u from User u where u.username= :username", User.class)
				.setParameter("username", username)
				.getSingleResult();
		return principal;
	}

}
