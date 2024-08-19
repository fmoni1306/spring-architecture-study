package org.example.springarchitecture.post.service.port;

import org.example.springarchitecture.post.infrastructure.PostEntity;

import java.util.Optional;

public interface PostRepository {

    Optional<PostEntity> findById(Long id);

    PostEntity save(PostEntity postEntity);
}
