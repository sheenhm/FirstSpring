package com.sheenhm.firstproject.controller;

import com.sheenhm.firstproject.dto.ArticleForm;
import com.sheenhm.firstproject.entity.Article;
import com.sheenhm.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "article/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository로 Entity를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환하기
        return "article/show";
    }

    @GetMapping("/articles")
    public String list(Model model) {
        // 1. 모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "article/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 1. 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        return "article/edit.mustache";
    }

    @PostMapping("articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository로 Entity를 DB에 저장
        Article target = articleRepository.findById(article.getId()).orElse(null);
        if (target != null) {
            articleRepository.save(article);
        }

        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("Article Deletion Requested");

        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 대상 Entity 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "게시물이 삭제되었습니다.");
        }

        return "redirect:/articles";
    }
}
