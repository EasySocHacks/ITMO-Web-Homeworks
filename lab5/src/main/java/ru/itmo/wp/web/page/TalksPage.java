package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    UserService userService = new UserService();
    TalkService talkService = new TalkService();

    private  void sendMessage(HttpServletRequest request) throws ValidationException {
        String targetUserLogin = request.getParameter("userSelection");
        String text = request.getParameter("text");
        User target = userService.findByLogin(targetUserLogin);
        long targetUserId = target.getId();
        long sourceUserId = ((User) request.getSession().getAttribute("user")).getId();

        talkService.validateMessage(text);

        talkService.save(new Talk(sourceUserId, targetUserId, text));
        setMessage("Message has been sent");

        throw new RedirectException("/talks");
    }

    @Override
    void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (request.getSession().getAttribute("user") == null) {
            setMessage("You must to enter a profile to visit this page");
            throw new RedirectException("/index");
        }

        view.put("users", userService.findAll());

        List<Talk> talks = talkService.findAll(((User) request.getSession().getAttribute("user")).getId());
        view.put("talks", talks);
    }
}
