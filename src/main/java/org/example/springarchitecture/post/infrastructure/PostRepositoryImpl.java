package org.example.springarchitecture.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.post.domain.Post;
import org.example.springarchitecture.post.service.port.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toModel();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id).map(PostEntity::toModel);
    }
}
