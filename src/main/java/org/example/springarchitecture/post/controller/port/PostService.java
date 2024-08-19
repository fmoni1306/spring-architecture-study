package org.example.springarchitecture.post.controller.port;

import org.example.springarchitecture.post.domain.Post;
import org.example.springarchitecture.post.domain.PostCreate;
import org.example.springarchitecture.post.domain.PostUpdate;

public interface PostService {

    Post getById(long id);

    Post create(PostCreate postCreate);

    Post update(long id, PostUpdate postUpdate);
}
