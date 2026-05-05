package com.study.basic.controller;

import com.study.basic.dto.ArticleForm;
import com.study.basic.entity.Article;
import com.study.basic.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm) {
//        System.out.println(articleForm.toString());
        // 1. DTO를 엔티티로 변환
        Article article = articleForm.toEntity();
        log.info(article.toString());
//        System.out.println(article.toString());
        // 2. 리포지토리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "";
    }
}
