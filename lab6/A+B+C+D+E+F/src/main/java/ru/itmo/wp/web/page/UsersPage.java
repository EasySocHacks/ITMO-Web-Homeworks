package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            user = userService.find(user.getId());

            view.put("user", user);
        }
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("foundUser",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeAdmin(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "You are not allowed to do this");
            throw new RedirectException("/index");
        }

        user = userService.find(user.getId());
        if (!user.isAdmin()) {
            request.getSession().setAttribute("message", "You are not allowed to do this");
            throw new RedirectException("/index");
        }

        long userId = Long.parseLong(request.getParameter("userId"));
        User actionUser = userService.find(userId);
        actionUser.setAdmin(!actionUser.isAdmin());

        userService.updateAdmin(actionUser);
    }
}
