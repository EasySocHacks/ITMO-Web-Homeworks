package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You must to enter to visit this page");
            throw new RedirectException("/index");
        }
    }

    private void save(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You must to enter to visit this page");
            throw new RedirectException("/index");
        }

        long userId = ((User) request.getSession().getAttribute("user")).getId();
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        Article article = new Article();
        article.setUserId(userId);
        article.setTitle(title);
        article.setText(text);
        article.setHidden(false);

        articleService.validateArticle(article);
        articleService.save(article);

        request.getSession().setAttribute("message", "Article has been created successfully");

        throw new RedirectException("/index");
    }
}
