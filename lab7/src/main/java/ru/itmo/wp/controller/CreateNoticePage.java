package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CreateNoticePage extends Page {
    private final NoticeService noticeService;

    public CreateNoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/createNotice")
    public String register(Model model) {
        model.addAttribute("createNoticeForm", new NoticeCredentials());
        return "CreateNoticePage";
    }

    @PostMapping("/createNotice")
    public String register(@Valid @ModelAttribute("createNoticeForm") NoticeCredentials noticeCredentials,
                           BindingResult bindingResult,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "CreateNoticePage";
        }

        noticeService.addNotice(noticeCredentials);
        putMessage(httpSession, "Notice has been successfully created");

        return "redirect:/";
    }
}
