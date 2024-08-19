package org.example.springarchitecture.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springarchitecture.user.domain.UserStatus;
import org.example.springarchitecture.user.domain.UserUpdate;
import org.example.springarchitecture.user.infrastructure.UserEntity;
import org.example.springarchitecture.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_없는채_전달_받을_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("fmoni1306@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("fmoni1306"))
                .andExpect(jsonPath("$.address").doesNotExist())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                        get("/api/users/3/verify")
                                .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                )
                .andExpect(status().isFound())
                .andExpect(content().string(""));

        UserEntity userEntity = userJpaRepository.findById(3L).get();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                        get("/api/users/me").header("EMAIL", "fmoni1306@gmail.com")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("fmoni1306@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("fmoni1306"))
                .andExpect(jsonPath("$.address").value("Seoul"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("taeyoon")
                .address("Busan")
                .build();
        //when
        //then
        mockMvc.perform(
                        put("/api/users/me")
                                .header("EMAIL", "fmoni1306@gmail.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(userUpdate))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("fmoni1306@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("taeyoon"))
                .andExpect(jsonPath("$.address").value("Busan"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}
