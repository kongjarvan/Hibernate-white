package site.metacoding.white.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.util.SHA256;

// 트랜젝션 관리
// DTO 변환하여 컨트롤러에게 리턴

@RequiredArgsConstructor
@Service // ioc컨테이너에 띄워줌
public class UserService {

	private final UserRepository userRepository;
	private final SHA256 sha256;

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
