package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // ioc 컨테이너 띄우기
public class UserRepository {

	private final EntityManager em;

	public void save(User user) {
		em.persist(user); // insert 시켜줌, update는 안됨
		// persistence Context에 영속화 시키기 -> 자동 flush (트랜젝션 종료시)
	}

}
