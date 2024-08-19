package org.example.springarchitecture.post.service;

import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.common.domain.exception.ResourceNotFoundException;
import org.example.springarchitecture.post.domain.Post;
import org.example.springarchitecture.post.domain.PostCreate;
import org.example.springarchitecture.post.domain.PostUpdate;
import org.example.springarchitecture.post.service.port.PostRepository;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User writer = userService.getById(postCreate.getWriterId());
        Post post = Post.from(writer, postCreate);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate);
        return postRepository.save(post);
    }
}
