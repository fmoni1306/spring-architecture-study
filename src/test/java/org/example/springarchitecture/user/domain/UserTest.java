package org.example.springarchitecture.user.domain;

import org.example.springarchitecture.common.domain.exception.CertificationCodeNotMatchedException;
import org.example.springarchitecture.mock.TestClockHolder;
import org.example.springarchitecture.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    void User는_UserCreate_객체로_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Busan")
                .build();

        //when
        User result = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        //then
        assertThat(result.getId()).isNull();
        assertThat(result.getEmail()).isEqualTo("fmoni1306@gmail.com");
        assertThat(result.getNickname()).isEqualTo("fmoni1306");
        assertThat(result.getAddress()).isEqualTo("Busan");
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void User는_UserUpdate_객체로_데이터를_업데이트_할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("fmoni1306-u")
                .address("Busan")
                .build();

        //when
        User result = user.update(userUpdate);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("fmoni1306@gmail.com");
        assertThat(result.getNickname()).isEqualTo("fmoni1306-u");
        assertThat(result.getAddress()).isEqualTo("Busan");
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        //when
        User result = user.login(new TestClockHolder(123123123123L));

        //then
        assertThat(result.getLastLoginAt()).isEqualTo(123123123123L);
    }

    @Test
    void User는_유효한_인증_코드로_계정을_활성화_할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        //when
        User result = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //then
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void User는_잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
        User user = User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        //when
        //then
        assertThatThrownBy(() -> user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1")
        ).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
