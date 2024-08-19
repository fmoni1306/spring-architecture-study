package org.example.springarchitecture.post.service.port;

import org.example.springarchitecture.post.domain.Post;

import java.util.Optional;

public interface PostRepository {

    Optional<Post> findById(Long id);

    Post save(Post post);
}
