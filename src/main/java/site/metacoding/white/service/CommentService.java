package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.CommentRepository;
import site.metacoding.white.dto.CommentReqDto.CommentSaveReqDto;
import site.metacoding.white.dto.CommentRespDto.CommentSaveRespDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;

	@Transactional // 이거 안붙이면 save가 안됨
	public CommentSaveRespDto save(CommentSaveReqDto commentSaveReqDto) {

		Long id = commentSaveReqDto.getBoardId();
		// 1. Board가 있는지 확인
		Optional<Board> boardOP = boardRepository.findById(commentSaveReqDto.getBoardId());
		if (boardOP.isPresent()) {
			// 2. 있으면 comment 객체 만들기
			Comment comment = commentSaveReqDto.toEntity(boardOP.get());
			Comment commentPS = commentRepository.save(comment);
			System.out.println(commentSaveReqDto.getContent());
			CommentSaveRespDto commentSaveRespDto = new CommentSaveRespDto(commentPS);

			return commentSaveRespDto;
		} else {
			throw new RuntimeException("해당" + id + "의 댓글작성을 할 수 없습니다.");
		}

	}

	@Transactional
	public void deleteById(Long id) {
		Optional<Comment> commentOP = commentRepository.findById(id);
		if (commentOP.isPresent()) {
			commentRepository.deleteById(id);
		} else {
			throw new RuntimeException("댓글" + id + "이 존재하지 않습니다.");
		}
	}

}
