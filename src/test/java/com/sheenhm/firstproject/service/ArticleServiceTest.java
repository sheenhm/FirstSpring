package com.sheenhm.firstproject.service;

import com.sheenhm.firstproject.dto.ArticleForm;
import com.sheenhm.firstproject.entity.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test
    @DisplayName("Show Articles' List Test")
    void index() {
        Article a = new Article(1L, "AAAA", "1111");
        Article b = new Article(2L, "BBBB", "2222");
        Article c = new Article(3L, "CCCC", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        List<Article> article = articleService.index();
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("Show Specific Article Test - Success")
    void show_success() {
        Long id = 1L;
        Article expected = new Article(id, "AAAA", "1111");
        Article article = articleService.show(id);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("Show Specific Article Test - Fail")
    void show_fail() {
        Long id = -1L;
        Article expected = null;
        Article article = articleService.show(id);
        assertEquals(expected, article);
    }

    @Test
    @DisplayName("Create New Article Test - Success")
    @Transactional
    void create_success() {
        String title = "DDDD";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        Article article = articleService.create(dto);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("Create New Article Test - Fail")
    @Transactional
    void create_fail() {
        Long id = 4L;
        String title = "DDDD";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        Article article = articleService.create(dto);
        assertEquals(expected, article);
    }
}