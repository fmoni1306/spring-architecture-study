package org.example.springarchitecture.post.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.common.domain.exception.ResourceNotFoundException;
import org.example.springarchitecture.common.service.port.ClockHolder;
import org.example.springarchitecture.post.controller.port.PostService;
import org.example.springarchitecture.post.domain.Post;
import org.example.springarchitecture.post.domain.PostCreate;
import org.example.springarchitecture.post.domain.PostUpdate;
import org.example.springarchitecture.post.service.port.PostRepository;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.service.port.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    @Override
    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    @Override
    public Post create(PostCreate postCreate) {
        User writer = userRepository.getById(postCreate.getWriterId());
        Post post = Post.from(writer, postCreate, clockHolder);
        return postRepository.save(post);
    }

    @Override
    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate, clockHolder);
        return postRepository.save(post);
    }
}
