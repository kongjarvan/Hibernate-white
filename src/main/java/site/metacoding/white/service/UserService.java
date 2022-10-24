package site.metacoding.white.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;

// 트랜젝션 관리
// DTO 변환하여 컨트롤러에게 리턴

@RequiredArgsConstructor
@Service // ioc컨테이너에 띄워줌
public class UserService {

	private final UserRepository userRepository;

	@Transactional // 이거 안붙이면 pc의 객체가 flush가 안됨
	public void save(User user) {
		userRepository.save(user);
	}

}
