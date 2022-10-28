package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class UserReqDto {

	@Getter
	@Setter
	public static class JoinReqDto { // 로그인 전 로직들은 전부다 앞에 엔티티 안붙임. POST /user/join -> /join
		private String username;
		private String password;

		public User toEntity() {
			return User.builder()
					.username(username)
					.password(password)
					.build();
		}
	}

	@Getter
	@Setter
	public static class LoginReqDto {
		private String username;
		private String password;
	}

	@Getter
	@Setter
	public static class UserUpdateReqDto {
		private Long id;
		private String username;
		private String password;

		public User toEntity() {
			return User.builder()
					.username(username)
					.password(password)
					.build();
		}
	}

}
