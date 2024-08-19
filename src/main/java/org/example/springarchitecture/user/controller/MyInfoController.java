package org.example.springarchitecture.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.user.controller.port.UserService;
import org.example.springarchitecture.user.controller.response.MyProfileResponse;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
public class MyInfoController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> getMyInfo(
            @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
            @RequestHeader("EMAIL") String email // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
    ) {
        User user = userService.getByEmail(email);
        userService.login(user.getId());
        return ResponseEntity
                .ok()
                .body(MyProfileResponse.from(user));
    }

    @PutMapping("/me")
    @Parameter(in = ParameterIn.HEADER, name = "EMAIL")
    public ResponseEntity<MyProfileResponse> updateMyInfo(
            @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
            @RequestHeader("EMAIL") String email, // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
            @RequestBody UserUpdate userUpdate
    ) {
        User user = userService.getByEmail(email);
        user = userService.update(user.getId(), userUpdate);
        return ResponseEntity
                .ok()
                .body(MyProfileResponse.from((user)));
    }
}
