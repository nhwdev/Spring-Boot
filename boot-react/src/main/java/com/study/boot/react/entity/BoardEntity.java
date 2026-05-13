package com.study.boot.react.entity;

import com.study.boot.react.dto.BoardDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class BoardEntity {
    /*
     * @GeneratedValue: 기본키의 값을 DB에 위임
     * strategy = GenerationType.IDENTITY: AUTO_INCREMENT 기능 사용
     * strategy = GenerationType.SEQUENCE: SEQUENCE 객체 사용. (오라클)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    private String name;
    private String pass;
    private String subject;
    private String content;
    private String file1;
    @Temporal(TemporalType.TIMESTAMP) // 생성된 날짜
    private Date regdate;
    private int readcnt;
    private String boardid;

    public BoardEntity(BoardDto boardDto) {
        this.num = boardDto.getNum();
        this.name = boardDto.getName();
        this.pass = boardDto.getPass();
        this.subject = boardDto.getSubject();
        this.content = boardDto.getContent();
        this.file1 = boardDto.getFile1();
        this.regdate = boardDto.getRegdate();
        this.readcnt = boardDto.getReadcnt();
        this.boardid = boardDto.getBoardid();
    }

    @PrePersist
    public void onPrePersist() {
        this.regdate = new Date();
    }

    public void patch(BoardEntity boardEntity) {
        if (boardEntity.getSubject() != null) this.subject = boardEntity.getSubject();
        if (boardEntity.getContent() != null) this.content = boardEntity.getContent();
        if (boardEntity.getFile1() != null) this.file1 = boardEntity.getFile1();
    }
}
