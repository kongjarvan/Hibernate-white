package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;
	private final HttpSession session;

	@PostMapping("/join")
	public ResponseDto<?> save(@RequestBody JoinReqDto joinReqDto) {
		JoinRespDto joinRespDto = userService.save(joinReqDto);
		return new ResponseDto<>(1, "ok", joinRespDto);
		// 내가 insert한 데이터를 body값으로 돌려줌
		// CREATED: 201(새로운것을 만들었을때)
	}

	@PostMapping("/login")
	public ResponseDto<?> login(@RequestBody LoginReqDto loginReqDto) {
		SessionUser principal = userService.login(loginReqDto);
		session.setAttribute("principal", principal);
		return new ResponseDto<>(1, "ok", principal);
	}

}
