package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;

        this.tagRepository = tagRepository;
        for (Tag.Name name : Tag.Name.values()) {
            if (tagRepository.countByName(name) == 0) {
                tagRepository.save(new Tag(name));
            }
        }
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findPostById(long id) {
        return postRepository.findPostById(id);
    }
}
