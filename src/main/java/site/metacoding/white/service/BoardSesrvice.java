package site.metacoding.white.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;

@RequiredArgsConstructor
@Service // ioc컨테이너에 띄워줌
public class BoardSesrvice {

	private final BoardRepository boardRepository;

	@Transactional// 이거 안붙이면 save가 안됨
	public void save(Board board) {
		boardRepository.save(board);
	}
}
