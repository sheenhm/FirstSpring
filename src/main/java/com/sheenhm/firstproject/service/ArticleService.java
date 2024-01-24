package com.sheenhm.firstproject.service;

import com.sheenhm.firstproject.dto.ArticleForm;
import com.sheenhm.firstproject.entity.Article;
import com.sheenhm.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. DTO 묶음을 Entity 묶음으로 변환하기
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());

        // 2. Entity 묶음을 DB에 저장하기
        articleList.stream().forEach(article -> articleRepository.save(article));

        // 3. 강제 예외 발생시키기 (Transaction Test)
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("INSERT FAIL - Transaction Test"));

        // 4. 결과 값 반환하기
        return articleList;
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("id : {}, article: {}", id, article.toString());

        Article target = articleRepository.findById(article.getId()).orElse(null);

        if (target == null || id != article.getId()) {
            log.info("WRONG Request - id: {}, article: {}", id, article.toString());
            return null;
        }

        target.patch(article);
        Article updated = articleRepository.save(article);

        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null) {
            return null;
        }

        articleRepository.delete(target);

        return target;
    }
}
