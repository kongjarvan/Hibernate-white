package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;

// 트랜젝션 관리
// DTO 변환하여 컨트롤러에게 리턴

@RequiredArgsConstructor
@Service // ioc컨테이너에 띄워줌
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional // 이거 안붙이면 save가 안됨
	public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {

		// 핵심 로직
		Board boardPS = boardRepository.save(boardSaveReqDto.toEntity()); // 서비스단에서 dto를 entity에 담아서 ps에 전달

		// DTO 전환
		BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);

		return boardSaveRespDto;
	}

	public Board findById(Long id) { // select만 할거면 transactional 안붙여도 됨
		return boardRepository.findById(id);
	}

	public List<Board> findAll() {
		return boardRepository.findAll();
	}

	@Transactional
	public void update(Long id, Board board) {
		Board boardPS = boardRepository.findById(id);
		boardPS.update(board.getTitle(), board.getContent());
	} // 종료시 더티체킹 하여 모든 쓰레기 데이터를 flush => update 됨

	@Transactional
	public void deleteById(Long id) {
		boardRepository.deleteById(id);
	}

}
