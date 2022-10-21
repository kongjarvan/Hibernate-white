package site.metacoding.white.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.service.BoardSesrvice;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

	private final BoardSesrvice boardService;

	@PostMapping("/board")
	public String save(@RequestBody Board board) {
		boardService.save(board);
		return "ok";
	}

}
