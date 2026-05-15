package com.study.boot.react.repository;

import com.study.boot.react.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, JpaSpecificationExecutor<CommentEntity> {
    List<CommentEntity> findAllByBoardNum(Integer boardNum);
}
