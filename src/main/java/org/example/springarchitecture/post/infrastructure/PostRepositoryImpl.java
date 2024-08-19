package org.example.springarchitecture.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.post.service.port.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postJpaRepository.save(postEntity);
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return postJpaRepository.findById(id);
    }
}
