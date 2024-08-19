package org.example.springarchitecture.user.controller.response;

import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResponseTest {

    @Test
    void User로_응답을_생성할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        //when
        UserResponse result = UserResponse.from(user);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("fmoni1306@gmail.com");
        assertThat(result.getNickname()).isEqualTo("fmoni1306");
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getLastLoginAt()).isEqualTo(100L);
    }
}
