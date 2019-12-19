package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping({"/post", "/post/"})
    public String getPost(HttpSession httpSession, Model model) {
        putMessage(httpSession, "No such post");
        return "redirect:/";
    }

    @Guest
    @GetMapping("/post/{id}")
    public String getPost(HttpSession httpSession, Model model, @PathVariable String id) {
        if (id != null && id != "") {
            try {
                Post post = postService.findPostById(Long.parseLong(id));

                if (post != null) {
                    model.addAttribute("commentForm", new Comment());

                    model.addAttribute("currPost", post);
                    model.addAttribute("comments", commentService.findAllCommentsByPost(post));

                    return "PostPage";
                }
            } catch (NumberFormatException ignored) {}
        }

        putMessage(httpSession, "No such post");
        return "redirect:/";
    }

    @PostMapping("/post/{id}")
    public String savePost(@Valid @ModelAttribute("commentForm") Comment commentForm,
                           BindingResult bindingResult,
                           HttpSession httpSession,
                           Model model,
                           @PathVariable String id) {
        if (id != null && id != "") {
            try {
                Post post = postService.findPostById(Long.parseLong(id));

                if (post != null) {
                    if (bindingResult.hasErrors()) {
                        model.addAttribute("commentForm", commentForm);
                        model.addAttribute("currPost", post);
                        model.addAttribute("comments", commentService.findAllCommentsByPost(post));
                        return "PostPage";
                    }

                    User user = (User) model.getAttribute("user");

                    Comment comment = new Comment();
                    comment.setPost(post);
                    comment.setUser(user);
                    comment.setText(commentForm.getText());

                    commentService.save(comment);

                    putMessage(httpSession, "Comment has been successfully saved");
                    return "redirect:/post/" + post.getId();
                }
            } catch (NumberFormatException ignored) {}
        }

        putMessage(httpSession, "No such post");
        return "redirect:/";
    }
}
