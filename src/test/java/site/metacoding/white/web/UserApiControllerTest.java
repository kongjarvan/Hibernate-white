package site.metacoding.white.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.service.UserService;

@ActiveProfiles("test")
// @Transactional // 통합테스트에서 랜덤포트를 사용하면 새로운 스레드로 돌기 때문에 롤백이 무의미함
@Sql("classpath:truncate.sql") // truncate: 테이블의 내용을 비움, 메서드가 종료될때 실행됨
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

	// @Autowired Junit 테스트에서는 autowired로 해야됨
	// private UserService userService;

	@Autowired
	private TestRestTemplate rt; // http커넥션 대체
	@Autowired
	private static ObjectMapper om; // json으로 데이터를 변환시켜줌
	@Autowired
	private static HttpHeaders headers;
	@Autowired
	private UserService userService;

	@BeforeAll // beforeAll은 static으로 만들어야 함
	public static void init() {
		om = new ObjectMapper();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void join_test() throws JsonProcessingException {
		// given
		JoinReqDto joinReqDto = new JoinReqDto();
		joinReqDto.setUsername("popo");
		joinReqDto.setPassword("1234");

		String body = om.writeValueAsString(joinReqDto);
		System.out.println("디버그: " + body);

		// when
		HttpEntity<String> request = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = rt.exchange("/join", HttpMethod.POST, request, String.class);

		// then

		System.out.println("디버그: " + response.getStatusCode());
		System.out.println("디버그: " + response.getBody());
		DocumentContext dc = JsonPath.parse(response.getBody());
		int code = dc.read("$.code");

		Assertions.assertThat(code).isEqualTo(1);
	}

	@Test
	public void login_test() throws JsonProcessingException {
		// data init
		JoinReqDto joinReqDto = new JoinReqDto();
		joinReqDto.setUsername("popo");
		joinReqDto.setPassword("1234");
		userService.save(joinReqDto);

		// given
		LoginReqDto loginReqDto = new LoginReqDto();
		loginReqDto.setUsername("popo");
		loginReqDto.setPassword("1234");
		String body = om.writeValueAsString(loginReqDto);
		System.out.println("디버그: " + body);

		// when
		HttpEntity<String> request = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = rt.exchange("/login", HttpMethod.POST, request, String.class);

		System.out.println("디버그: " + response.getBody());

		// then
		DocumentContext dc = JsonPath.parse(response.getBody());
		int code = dc.read("$.code");
		String username = dc.read("$.data.username");
		Assertions.assertThat(code).isEqualTo(1);
		Assertions.assertThat(username).isEqualTo("popo");
	}
}
