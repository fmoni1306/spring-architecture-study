package org.example.springarchitecture.post.domain;

import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    void PostCreate로_게시물을_만들_수_있다() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        User writer = User.builder()
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        //when
        Post result = Post.from(writer ,postCreate);

        //then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("fmoni1306@gmail.com");
        assertThat(result.getWriter().getNickname()).isEqualTo("fmoni1306");
        assertThat(result.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(result.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}
