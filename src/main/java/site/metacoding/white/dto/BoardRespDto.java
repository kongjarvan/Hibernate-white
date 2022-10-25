package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto.UserDto;

public class BoardRespDto {

	@Setter
	@Getter
	public static class BoardSaveRespDto {
		private Long id;
		private String title;
		private String content;
		private UserDto user;

		@Setter
		@Getter
		public static class UserDto {
			private Long id;
			private String username;

			public UserDto(User user) {
				this.id = user.getId();
				this.username = user.getUsername();
			}
		}

		public BoardSaveRespDto(Board board) {
			this.id = board.getId();
			this.title = board.getTitle();
			this.content = board.getContent();
			this.user = new UserDto(board.getUser());
		}

		// 메서드 만들어서 한번에 해결하기

	}

	@Setter
	@Getter
	public static class BoardUpdateRespDto {
		private Long id;
		private String title;
		private String content;
		private UserDto user;

		@Setter
		@Getter
		public static class UserDto {
			private Long id;
			private String username;

			public UserDto(User user) {
				this.id = user.getId();
				this.username = user.getUsername();
			}
		}

		public BoardUpdateRespDto(Board board) {
			this.id = board.getId();
			this.title = board.getTitle();
			this.content = board.getContent();
			this.user = new UserDto(board.getUser());
		}
	}
}
