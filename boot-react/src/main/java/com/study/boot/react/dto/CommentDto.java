package com.study.boot.react.dto;

import com.study.boot.react.entity.BoardEntity;
import com.study.boot.react.entity.CommentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentDto {
    private Integer num;
    private Integer boardNum;
    private String name;
    private String pass;
    private String content;
    private Date regdate;

    // 엔티티를 DTO로 변환해주는 생성자
    public CommentDto(CommentEntity entity) {
        this.num = entity.getNum();
        this.name = entity.getName();
        this.content = entity.getContent();
        this.regdate = entity.getRegdate();
        // 외래키는 객체에서 번호만 꺼내서 저장
        this.boardNum = entity.getBoard().getNum();
    }

    // DTO를 Entity로 변환해주는 함수
    public CommentEntity toEntity(BoardEntity boardEntity) {
        return CommentEntity.builder()
                .name(this.name)
                .pass(this.pass)
                .content(this.content)
                .board(boardEntity)
                .build();
    }
}
