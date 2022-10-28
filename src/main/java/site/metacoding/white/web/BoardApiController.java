package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardUpdateRespDto;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

	private final BoardService boardService;
	private final HttpSession session;

	@GetMapping("/board/{id}")
	public ResponseDto<?> findById(@PathVariable Long id) {
		return new ResponseDto<>(1, "성공", boardService.findById(id));
	}

	@GetMapping("/board")
	public ResponseDto<?> findAll() {
		return new ResponseDto<>(1, "성공", boardService.findAll());
	}

	@PutMapping("/board/{id}")
	public ResponseDto<?> update(@PathVariable Long id, @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		if (sessionUser == null) {
			throw new RuntimeException("로그인이 필요합니다");
		}
		boardUpdateReqDto.setId(id);
		BoardUpdateRespDto boardUpdateRespDto = boardService.update(boardUpdateReqDto);
		return new ResponseDto<>(1, "성공", boardUpdateRespDto);
	}

	@PostMapping("/board")
	public ResponseDto<?> save(@RequestBody BoardSaveReqDto boardSaveReqDto) {
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		if (sessionUser == null) {
			throw new RuntimeException("로그인이 필요합니다");
		}
		boardSaveReqDto.setSessionUser(sessionUser); // 서비스에는 단 하나의 객체만 전달한다
		BoardSaveRespDto boardSaveRespDto = boardService.save(boardSaveReqDto);
		return new ResponseDto<>(1, "성공", boardSaveRespDto);
	}

	@DeleteMapping("/board/{id}")
	public ResponseDto<?> deleteById(@PathVariable Long id) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		if (sessionUser == null) {
			throw new RuntimeException("로그인이 필요합니다");
		}
		boardService.deleteById(id);
		return new ResponseDto<>(1, "성공", null);
	}

}
