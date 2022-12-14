package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.CommentReqDto.CommentSaveReqDto;
import site.metacoding.white.dto.CommentRespDto.CommentSaveRespDto;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.service.CommentService;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	private final CommentService commentService;
	private final HttpSession session;

	@PostMapping("/board/{id}/comment")
	public ResponseDto<?> save(@PathVariable Long id, @RequestBody CommentSaveReqDto commentSaveReqDto) {
		commentSaveReqDto.setBoardId(id);
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		commentSaveReqDto.setSessionUser(sessionUser);
		CommentSaveRespDto commentSaveRespDto = commentService.save(commentSaveReqDto);
		return new ResponseDto<>(1, "성공", commentSaveRespDto);
	}

	@DeleteMapping("/comment/{id}")
	public ResponseDto<?> deleteById(@PathVariable Long id) {
		commentService.deleteById(id);
		return new ResponseDto<>(1, "성공", null);
	}
}
