package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "You must to enter to visit this page");
            throw new RedirectException("/index");
        }

        view.put("myArticles", articleService.findAllByUserId(((User) user).getId()));
    }

    private void changeStatus(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "You must to enter to visit this page");
            throw new RedirectException("/index");
        }

        long id = Long.parseLong(request.getParameter("id"));
        Article article = articleService.find(id);

        if (article.getUserId() != user.getId()) {
            request.getSession().setAttribute("message", "Something goes wrong. Try it again");
            throw new RedirectException("/myArticles");
        }

        article.setHidden(!article.isHidden());
        articleService.updateStatus(article);
    }

    private void isHidden(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You must to enter to visit this page");
            throw new RedirectException("/index");
        }

        view.put("isHidden", articleService.find(Long.parseLong(request.getParameter("id"))).isHidden());
    }
}
