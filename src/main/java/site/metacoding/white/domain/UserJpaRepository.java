package site.metacoding.white.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	@Query(value = "select u from User u where u.username = :username")
	// findBy는 하나의 문법으로 이 뒤에 컬럼명을 입력하면 쿼리 어노테이션은 안써도 기능함
	User findByUsername(@Param("username") String username);
}
