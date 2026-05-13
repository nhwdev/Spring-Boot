package com.study.boot.react.repository;

import com.study.boot.react.entity.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, JpaSpecificationExecutor<BoardEntity> {
    @Transactional
    @Modifying
    @Query("UPDATE BoardEntity b SET b.readcnt = b.readcnt + 1 WHERE b.num = :num")
    void addReadCount(@Param("num") int num);
}
