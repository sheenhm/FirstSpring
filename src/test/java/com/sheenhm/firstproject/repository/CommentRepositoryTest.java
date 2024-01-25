package com.sheenhm.firstproject.repository;

import com.sheenhm.firstproject.entity.Article;
import com.sheenhm.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 Article의 모든 Comment 조회")
    void findByArticleId() {
        Long articleId = 4L;
        Article article = new Article(4L, "당신의 인생 영화는?", "댓글ㄱ");
        Comment a = new Comment(1L, article, "Park", "찰리와 초콜릿 공장");
        Comment b = new Comment(2L, article, "Kim", "쇼생크 탈출");
        Comment c = new Comment(3L, article, "Choi", "아일랜드");
        List<Comment> expected = Arrays.asList(a, b, c);
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        assertEquals(expected.toString(), comments.toString());
    }

    @Test
    @DisplayName("특정 Nickname의 모든 Comment 조회")
    void findByNickName() {
        String nickname = "Park";
        Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글ㄱ"), nickname, "찰리와 초콜릿 공장");
        Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글ㄱㄱ"), nickname, "치킨");
        Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글ㄱㄱㄱ"), nickname, "조깅");
        List<Comment> expected = Arrays.asList(a, b, c);
        List<Comment> comments = commentRepository.findByNickName(nickname);
        assertEquals(expected.toString(), comments.toString());
    }
}
