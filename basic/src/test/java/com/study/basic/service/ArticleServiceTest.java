package com.study.basic.service;

import com.study.basic.dto.ArticleForm;
import com.study.basic.entity.Article;
import org.junit.jupiter.api.Test; // Test 패키지 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // 앞으로 사용할 수 있는 패키지 임포트

        // 1. 예상 데이터
        // 2. 실제 데이터
        // 3. 비교 및 검증

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 1. 예상 데이터
        Article a = new Article(1L, "리틀 트윈스타", "리틀트윈스타는 꿈별구름의 배려하기 별에서 태어난 쌍둥이 남매 별입니다.");
        Article b = new Article(2L, "마이멜로디", "마이멜로디는 밝고 명랑하며 남동생을 아끼는 여자아이예요.");
        Article c = new Article(3L, "포차코", "호기심 많은 참견쟁이로 산책을 좋아하는 남자 강아지예요.");
        Article d = new Article(4L, "한교동", "중국에서 태어난 반어인(半魚人). 사람을 웃게 하는 게 특기지만 사실은 외로움을 타는 로맨티스트.");
        Article e = new Article(5L, "폼폼푸린", "폼폼푸린은 진갈색의 베레모가 트레이드 마크인 골든 리트리버입니다.");
        Article f = new Article(6L, "쿠로미", "자칭 마이멜로디의 라이벌인 쿠로미.");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c, d, e, f));
        // 2. 실제 데이터
        List<Article> articles = articleService.index();
        // 3. 비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(1L, "리틀 트윈스타", "리틀트윈스타는 꿈별구름의 배려하기 별에서 태어난 쌍둥이 남매 별입니다.");
        // 2. 실제 데이터
        Article article = articleService.show(id);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패_존재하지_않는_id_입력() {
        // 1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.show(id);
        // 3. 비교 및 검증
        assertEquals(expected, article);

    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        // 1. 예상 데이터
        String title = "쿠로미";
        String content = "자칭 마이멜로디의 라이벌인 쿠로미.";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(7L, title, content);
        // 2. 실제 데이터
        Article article = articleService.create(dto);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        // 1. 예상 데이터
        Long id = 7L;
        String title = "쿠로미";
        String content = "자칭 마이멜로디의 라이벌인 쿠로미.";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.create(dto);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title과_content가_있는_dto_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        String title = "마이멜로디";
        String content = "호기심 많은 참견쟁이로 산책을 좋아하는 남자 강아지예요.";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(1L, "마이멜로디", "호기심 많은 참견쟁이로 산책을 좋아하는 남자 강아지예요.");
        // 2. 실제 데이터
        Article article = articleService.update(id, dto);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        String title = "마이멜로디";
        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(1L, "마이멜로디", "리틀트윈스타는 꿈별구름의 배려하기 별에서 태어난 쌍둥이 남매 별입니다.");
        // 2. 실제 데이터
        Article article = articleService.update(id, dto);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력() {
        // 1. 예상 데이터
        Long id = -1L;
        String title = "마이멜로디";
        String content = "호기심 많은 참견쟁이로 산책을 좋아하는 남자 강아지예요.";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.update(id, dto);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(1L, "리틀 트윈스타", "리틀트윈스타는 꿈별구름의 배려하기 별에서 태어난 쌍둥이 남매 별입니다.");
        // 2. 실제 데이터
        Article article = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력() {
        // 1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }
}