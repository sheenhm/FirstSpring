package com.sheenhm.firstproject.service;

import com.sheenhm.firstproject.dto.CommentDto;
import com.sheenhm.firstproject.entity.Article;
import com.sheenhm.firstproject.entity.Comment;
import com.sheenhm.firstproject.repository.ArticleRepository;
import com.sheenhm.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. Article 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("COMMENT FAIL - Article NOT EXIST"));

        // 2. Comment Entity 생성
        Comment comment = Comment.createComment(dto, article);

        // 3. Comment Entity를 DB에 저장
        Comment created = commentRepository.save(comment);

        // 4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long commentId, CommentDto dto) {
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("COMMENT UPDATE FAIL - Comment NOT EXIST"));
        target.patch(dto);
        Comment updated = commentRepository.save(target);
        return CommentDto.createCommentDto(updated);
    }

    public CommentDto delete(Long commentId) {
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("COMMNET DELETE FAIL - Comment NOT EXIST"));
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }
}
