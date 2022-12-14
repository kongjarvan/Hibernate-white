package site.metacoding.white.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardUpdateRespDto;

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

	@Transactional
	public BoardDetailRespDto findById(Long id) {
		Optional<Board> boardOP = boardRepository.findById(id);
		if (boardOP.isPresent()) {
			BoardDetailRespDto boardDetailRespDto = new BoardDetailRespDto(boardOP.get());
			return boardDetailRespDto;
		} else {
			throw new RuntimeException("해당" + id + "로 상세보기를 할 수 없습니다.");
		}
	}

	@Transactional
	public List<BoardAllRespDto> findAll() {
		List<Board> boardList = boardRepository.findAll();

		List<BoardAllRespDto> boardAllRespDtoList = new ArrayList<>();
		// 1. for문 돌리기(list의 크기만큼)
		for (Board board : boardList) { // Board 변수선언(board) : 배열명
			boardAllRespDtoList.add(new BoardAllRespDto(board));
		}
		return boardAllRespDtoList;
		// 2. Board -> Dto로 옮기기
		// 3. Dto를 list에 담기
	}

	@Transactional
	public BoardUpdateRespDto update(BoardUpdateReqDto boardUpdateReqDto) {
		Long id = boardUpdateReqDto.getId();
		Optional<Board> boardOP = boardRepository.findById(boardUpdateReqDto.getId());
		if (boardOP.isPresent()) {
			Board boardPS = boardOP.get();
			boardPS.update(boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent());
			return new BoardUpdateRespDto(boardPS);
		} else {
			throw new RuntimeException("해당" + id + "로 업데이트를 할 수 없습니다.");
		}

	} // 종료시 더티체킹 하여 모든 쓰레기 데이터를 flush => update 됨

	// delete는 리턴 안함
	@Transactional
	public void deleteById(Long id) {
		Optional<Board> boardOP = boardRepository.findById(id);
		if (boardOP.isPresent()) {
			boardRepository.deleteById(id);
		} else {
			throw new RuntimeException("해당" + id + "로 삭제를 할 수 없습니다.");
		}
	}

}
