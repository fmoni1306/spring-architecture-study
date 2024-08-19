package org.example.springarchitecture.medium;

import org.example.springarchitecture.common.domain.exception.CertificationCodeNotMatchedException;
import org.example.springarchitecture.common.domain.exception.ResourceNotFoundException;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserCreate;
import org.example.springarchitecture.user.domain.UserStatus;
import org.example.springarchitecture.user.domain.UserUpdate;
import org.example.springarchitecture.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
        User result = userService.getById(2);

        //then
        assertThat(result.getNickname()).isEqualTo("fmoni1306");
    }

    @Test
    void getById는_PENDING_상태인_유저를_찾아올_수_없다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            User result = userService.getById(3);
        }).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void userCreate_를_이용하여_유저를_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate
                .builder()
                .email("fmoni3306@gmail.com")
                .address("Seoul")
                .nickname("fmoni1306-k")
                .build();

        //when
        User result = userService.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
//        assertThat(result.getCertificationCode()).isEqualTo("T.T");
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
        User result = userService.update(2, userUpdate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getAddress()).isEqualTo("Busan");
        assertThat(result.getNickname()).isEqualTo("fmoni1306-n");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        //given
        //when
        userService.login(2);

        //then
        User user = userService.getById(2);
        assertThat(user.getLastLoginAt()).isGreaterThan(0L);
//        assertThat(userEntity.getLastLoginAt()).isEqualTo("T.T); FIXME
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        //given
        //when
        userService.verify(3, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        //then
        User user = userService.getById(3);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userService.verify(3, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
