package org.example.springarchitecture.post.controller;

import org.example.springarchitecture.mock.TestContainer;
import org.example.springarchitecture.post.controller.response.PostResponse;
import org.example.springarchitecture.post.domain.PostCreate;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1679530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build());
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        // when
        ResponseEntity<PostResponse> result = testContainer.postCreateController.create(postCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("fmoni1306");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1679530673958L);
    }
}
