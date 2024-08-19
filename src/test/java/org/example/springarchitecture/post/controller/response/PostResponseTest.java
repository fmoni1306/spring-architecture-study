package org.example.springarchitecture.post.controller.response;

import org.example.springarchitecture.post.domain.Post;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostResponseTest {

    @Test
    void Post로_응답을_생성할_수_있다() {
        //given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .email("fmoni1306@gmail.com")
                        .nickname("fmoni1306")
                        .address("Seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                        .build())
                .build();
        //when

        PostResponse result = PostResponse.from(post);

        //then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("fmoni1306@gmail.com");
        assertThat(result.getWriter().getNickname()).isEqualTo("fmoni1306");
        assertThat(result.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
