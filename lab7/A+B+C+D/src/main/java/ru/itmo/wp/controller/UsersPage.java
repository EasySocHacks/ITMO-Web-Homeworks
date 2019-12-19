package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisableCredentials;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("disableCredentials", new DisableCredentials());
        return "UsersPage";
    }

    @PostMapping("users/all")
    public String changeMode(@Valid @ModelAttribute("disableCredentials") DisableCredentials disableCredentials, HttpSession httpSession) {
        userService.updateDisableMode(disableCredentials.getId(), !disableCredentials.isDisable());
        putMessage(httpSession, "User with id#" + disableCredentials.getId() + " has been successfully " + (disableCredentials.isDisable() ? "Enabled" : "Disabled"));
        return "redirect:/users/all";
    }
}
