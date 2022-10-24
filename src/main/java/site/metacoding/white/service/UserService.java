package site.metacoding.white.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.UserReqDto.JoinDto;

// 트랜젝션 관리
// DTO 변환하여 컨트롤러에게 리턴

@RequiredArgsConstructor
@Service // ioc컨테이너에 띄워줌
public class UserService {

	private final UserRepository userRepository;

	@Transactional // 이거 안붙이면 pc의 객체가 flush가 안됨
	public void save(JoinDto joinDto) {
		User user = new User();
		user.setUsername(joinDto.getUsername());
		user.setPassword(joinDto.getPassword());
		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User login(User user) {
		User userPS = userRepository.findByUsername(user);
		if (userPS.getPassword().equals(user.getPassword())) {
			return userPS;
		} else {
			throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력 되었습니다.");
		}
	}

}
