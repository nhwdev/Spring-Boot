package com.study.boot.react.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;        // 댓글 고유 번호 (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardnum", referencedColumnName = "num", nullable = false)
    private BoardEntity board;   // 어떤 게시글의 댓글인지 (상위 게시글 번호)

    private String name;    // 댓글 작성자
    private String pass;    // 댓글 비밀번호 (수정/삭제용)
    private String content; // 댓글 내용

    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;   // 댓글 작성일

    // 엔티티가 처음 저장될 때 자동으로 현재 시간 입력
    @PrePersist
    public void onPrePersist() {
        this.regdate = new Date();
    }

    // 댓글 수정 시 내용만 수정 가능하도록 간단히 처리
    public void patch(CommentEntity incomingEntity) {
        if (incomingEntity.getContent() != null) {
            this.content = incomingEntity.getContent();
        }
    }
}