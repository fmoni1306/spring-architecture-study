package org.example.springarchitecture.user.service;

import org.example.springarchitecture.common.domain.exception.CertificationCodeNotMatchedException;
import org.example.springarchitecture.common.domain.exception.ResourceNotFoundException;
import org.example.springarchitecture.mock.FakeMailSender;
import org.example.springarchitecture.mock.FakeUserRepository;
import org.example.springarchitecture.mock.TestClockHolder;
import org.example.springarchitecture.mock.TestUuidHolder;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserCreate;
import org.example.springarchitecture.user.domain.UserStatus;
import org.example.springarchitecture.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceTest {

    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        this.userService = UserServiceImpl.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .clockHolder(new TestClockHolder(123123456L))
                .userRepository(fakeUserRepository)
                .certificationService(new CertificationService(fakeMailSender))
                .build();

        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("fmoni2306@gmail.com")
                .nickname("fmoni2306")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());

    }

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "fmoni1306@gmail.com";

        //when
        User result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("fmoni1306");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저를_찾아올_수_없다() {
        //given
        String email = "fmoni2306@naver.com";

        //when
        //then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //when
        User result = userService.getById(1);

        //then
        assertThat(result.getNickname()).isEqualTo("fmoni1306");
    }

    @Test
    void getById는_PENDING_상태인_유저를_찾아올_수_없다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            User result = userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void userCreate_를_이용하여_유저를_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate
                .builder()
                .email("fmoni1306@gmail.com")
                .address("Seoul")
                .nickname("fmoni1306-k")
                .build();

        //when
        User result = userService.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void userCreate_를_이용하여_유저를_수정할_수_있다() {
        //given
        UserUpdate userUpdate = UserUpdate
                .builder()
                .address("Busan")
                .nickname("fmoni1306-n")
                .build();

        //when
        User result = userService.update(1, userUpdate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getAddress()).isEqualTo("Busan");
        assertThat(result.getNickname()).isEqualTo("fmoni1306-n");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        //given
        //when
        userService.login(1);

        //then
        User result = userService.getById(1);
        assertThat(result.getLastLoginAt()).isGreaterThan(0L);
        assertThat(result.getLastLoginAt()).isEqualTo(123123456L);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        //given
        //when
        userService.verify(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        //then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userService.verify(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
