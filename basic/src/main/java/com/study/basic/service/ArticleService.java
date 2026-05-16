package com.study.basic.service;

import com.study.basic.dto.ArticleForm;
import com.study.basic.entity.Article;
import com.study.basic.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm articleForm) {
        Article article = articleForm.toEntity();
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(long id, ArticleForm articleForm) {
        // 1. DTO → 엔티티 변환하기
        Article article = articleForm.toEntity();
        log.info("id: {}, articles: {}", id, article.toString());
        // 2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리하기
        if(target == null || article.getId() != id) {
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }
        // 4. 업데이트 및 정상 응답(200)하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return null;
        }
        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> articleForms) {
        // 1. dto 묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList = articleForms.stream().map(ArticleForm::toEntity).collect(Collectors.toList());
        // 2. 엔티티 묶음을 DB에 저장하기
        articleList.stream().forEach(article -> articleRepository.save(article));
        /*
         * - 방법 1
         * for (int i = 0; i< articleList.size(); i++) {
         *      Article article = articleList.get(i);
         *      articleRepository.save(article);
         * }
         * - 방법 2
         * articleRepository.saveAll(articleList);
         */
        // 3. 강제 예외 발생 시키기
        articleRepository.findById(-1L).orElseThrow(()-> new IllegalArgumentException("결제 실패!"));
        // 4. 결과 값 반환하기
        return articleList;
    }
}
