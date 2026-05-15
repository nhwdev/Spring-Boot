package com.study.boot.react.controller;

import com.study.boot.react.dto.CommentDto;
import com.study.boot.react.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public ResponseEntity<?> commentList(@RequestParam Integer boardNum) {
        try {
            List<CommentDto> commentList = commentService.commentList(boardNum);
            return ResponseEntity.status(HttpStatus.OK).body(commentList);
        } catch (Exception e) {
            log.error("댓글 목록 조회중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/commentPro")
    public ResponseEntity<?> commentPro(@RequestBody CommentDto commentDto) {
        try {
            commentService.insertComment(commentDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/commentDeletePro")
    public ResponseEntity<?> commentDeletePro(@RequestBody CommentDto commentDto) {
        commentService.deleteComment(commentDto);
        return ResponseEntity.ok().build();
    }
}
