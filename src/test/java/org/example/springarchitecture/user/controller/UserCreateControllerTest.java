package org.example.springarchitecture.user.controller;

import org.example.springarchitecture.mock.TestContainer;
import org.example.springarchitecture.user.controller.response.UserResponse;
import org.example.springarchitecture.user.domain.UserCreate;
import org.example.springarchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        UserCreate userCreate = UserCreate.builder()
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Busan")
                .build();

        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.create(userCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("fmoni1306@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("fmoni1306");
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(testContainer.userRepository.getById(1L).getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }
}
