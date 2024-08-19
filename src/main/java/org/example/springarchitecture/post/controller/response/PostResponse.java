package org.example.springarchitecture.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.example.springarchitecture.post.domain.Post;
import org.example.springarchitecture.user.controller.response.UserResponse;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private UserResponse writer;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(UserResponse.from(post.getWriter()))
                .build();
    }
}
