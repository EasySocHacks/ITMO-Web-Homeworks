package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    private final UserService userService = new UserService();
    private HttpServletRequest request;

    private void action() {
        // No operations.
    }

    void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;

        view.put("userCount", userService.findCount());

        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }

        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }
    void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    public final void setMessage(String message) {
        request.getSession().setAttribute("message", message);;
    }

    public final void setUser(User user) throws ValidationException {
        request.getSession().setAttribute("user", user);
    }

    public final User getUser() {
        return (User) request.getSession().getAttribute("user");
    }
}
