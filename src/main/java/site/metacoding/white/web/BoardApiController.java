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
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.BoardReqDto.BoardSaveDto;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

	private final BoardService boardService;
	private final HttpSession session;

	@GetMapping("/v2/board/{id}")
	public Board findByIdV2(@PathVariable Long id) {
		Board boardPS = boardService.findById(id);
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
	public String update(@PathVariable Long id, @RequestBody Board board) {
		boardService.update(id, board);
		return "ok";
	}

	@PostMapping("/v2/board")
	public String saveV2(@RequestBody BoardSaveDto boardSaveDto) {
		User principal = (User) session.getAttribute("principal");
		// insert into user(title, content, user_id) values (?, ?, ?)
		boardSaveDto.setUser(principal);
		boardService.save(boardSaveDto);
		return "ok";
	}

	/*
	 * @PostMapping("/board")
	 * public String save(@RequestBody Board board) {
	 * boardService.save(board);
	 * return "ok";
	 * }
	 */

	@DeleteMapping("/board/{id}")
	public String deleteById(@PathVariable Long id) {
		boardService.deleteById(id);
		return "ok";
	}

}
