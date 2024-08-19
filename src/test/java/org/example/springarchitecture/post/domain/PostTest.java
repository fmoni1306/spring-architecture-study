package org.example.springarchitecture.post.domain;

import org.example.springarchitecture.mock.TestClockHolder;
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
        Post result = Post.from(writer ,postCreate, new TestClockHolder(1678530673958L));

        //then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(result.getWriter().getEmail()).isEqualTo("fmoni1306@gmail.com");
        assertThat(result.getWriter().getNickname()).isEqualTo("fmoni1306");
        assertThat(result.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(result.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void PostUpdate로_게시물을_수정_할_수_있다() {
        //given
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();
        User writer = User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        // when
        post = post.update(postUpdate, new TestClockHolder(1679530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("foobar");
        assertThat(post.getModifiedAt()).isEqualTo(1679530673958L);
    }
}
