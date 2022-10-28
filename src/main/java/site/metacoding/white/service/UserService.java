package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserReqDto.UserUpdateReqDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.dto.UserRespDto.UserDetailRespDto;
import site.metacoding.white.dto.UserRespDto.UserUpdateRespDto;
import site.metacoding.white.util.SHA256;

// 트랜젝션 관리
// DTO 변환하여 컨트롤러에게 리턴

@RequiredArgsConstructor
@Service // ioc컨테이너에 띄워줌
public class UserService {

	private final UserRepository userRepository;
	private final SHA256 sha256;

	@Transactional
	public UserUpdateRespDto update(UserUpdateReqDto userUpdateReqDto) {
		// 비밀번호 해시
		Long id = userUpdateReqDto.getId();
		Optional<User> userOP = userRepository.findById(userUpdateReqDto.getId());
		if (userOP.isPresent()) {
			User userPS = userOP.get();
			String encPassword = sha256.encrypt(userUpdateReqDto.getPassword());
			userPS.update(userUpdateReqDto.getUsername(), encPassword);
			return new UserUpdateRespDto(userPS);
		} else {
			throw new RuntimeException("해당" + id + "의 유저가 존재하지 않습니다.");
		}
	}

	@Transactional
	public UserDetailRespDto findById(Long id) {
		Optional<User> userOP = userRepository.findById(id);
		if (userOP.isPresent()) {
			return new UserDetailRespDto(userOP.get());
		} else {
			throw new RuntimeException("해당" + id + "로 유저 정보 보기를 할 수 없습니다.");
		}

	}

	// 응답의 DTO는 서비스에서 만든다.
	@Transactional // 이거 안붙이면 pc의 객체가 flush가 안됨
	public JoinRespDto save(JoinReqDto joinReqDto) {
		// 비밀번호 해시
		String encPassword = sha256.encrypt(joinReqDto.getPassword());
		joinReqDto.setPassword(encPassword);

		// 회원정보 저장
		User userPS = userRepository.save(joinReqDto.toEntity());
		return new JoinRespDto(userPS);
	}

	@Transactional(readOnly = true)
	public SessionUser login(LoginReqDto loginReqDto) {
		User userPS = userRepository.findByUsername(loginReqDto.getUsername());
		String encPassword = sha256.encrypt(loginReqDto.getPassword());
		if (userPS.getUsername() != null && userPS.getPassword().equals(encPassword)) {
			return new SessionUser(userPS);
		} else {
			throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력 되었습니다.");
		}
	}

}
