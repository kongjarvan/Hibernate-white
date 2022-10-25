package site.metacoding.white.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
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

	@GetMapping("/v2/board/{id}")
	public Board findByIdV2(@PathVariable Long id) {
		Board boardPS = boardService.findById(id);
		System.out.println("이 아래 lazy 로딩");
		boardPS.getUser().getUsername(); // lazy 로딩 (eager시에는 필요 없음)
		return boardService.findById(id);
	}

	@GetMapping("/board/{id}")
	public Board findById(@PathVariable Long id) {
		return boardService.findById(id);
	}

	@GetMapping("/boardList")
	public List<Board> findAll() {
		return boardService.findAll();
	}

	@PutMapping("/board/{id}")
	public ResponseDto<?> update(@PathVariable Long id, @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("principal");
		boardUpdateReqDto.setSessionUser(sessionUser);
		BoardUpdateRespDto boardUpdateRespDto = boardService.update(boardUpdateReqDto);
		return new ResponseDto<>(1, "성공", boardUpdateRespDto);
	}

	@PostMapping("/board")
	public ResponseDto<?> save(@RequestBody BoardSaveReqDto boardSaveReqDto) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("principal");
		boardSaveReqDto.setSessionUser(sessionUser); // 서비스에는 단 하나의 객체만 전달한다
		BoardSaveRespDto boardSaveRespDto = boardService.save(boardSaveReqDto);
		return new ResponseDto<>(1, "성공", boardSaveRespDto);
	}

	@DeleteMapping("/board/{id}")
	public String deleteById(@PathVariable Long id) {
		boardService.deleteById(id);
		return "ok";
	}

}
