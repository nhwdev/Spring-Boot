package com.study.boot.react.service;

import com.study.boot.react.dto.CommentDto;
import com.study.boot.react.entity.BoardEntity;
import com.study.boot.react.entity.CommentEntity;
import com.study.boot.react.repository.BoardRepository;
import com.study.boot.react.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    public List<CommentDto> commentList(Integer boardNum) {
        List<CommentEntity> comments = commentRepository
                .findAllByBoardNum(boardNum);
        if (comments.isEmpty()) {
            throw new IllegalArgumentException("댓글이 없습니다.");
        } else {
            return comments.stream()
                    .map(CommentDto::new)
                    .collect(Collectors.toList());
        }
    }

    public void insertComment(CommentDto commentDto) {
        BoardEntity boardEntity = boardRepository
                .findById(commentDto.getBoardNum())
                .orElseThrow(() -> new RuntimeException("해당 번호(" + commentDto.getBoardNum() + ")의 게시글을 찾을 수 없습니다."));
        commentRepository.save(commentDto.toEntity(boardEntity));
    }

    public void deleteComment(CommentDto commentDto) {
        CommentEntity target = commentRepository.findById(commentDto.getNum()).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        target.authenticate(commentDto.getPass());
        commentRepository.delete(target);
    }
}
