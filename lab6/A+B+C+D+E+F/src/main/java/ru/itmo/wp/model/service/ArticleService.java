package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllByUserId(long userId) {
        return  articleRepository.findAllByUserId(userId);
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public void updateStatus(Article article) {
        articleRepository.updateStatus(article);
    }

    public void validateArticle(Article article) throws ValidationException {
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title must not be an empty string");
        }

        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text must not be an empty string");
        }

        if (article.getTitle().length() > Article.TITLE_MAX_LENGTH) {
            throw new ValidationException("Title must be not exceed " + Article.TITLE_MAX_LENGTH + " symbols length");
        }

        if (article.getText().length() > Article.TEXT_MAX_LENGTH) {
            throw new ValidationException("Text must be not exceed " + Article.TITLE_MAX_LENGTH + " symbols length");
        }
    }
}
