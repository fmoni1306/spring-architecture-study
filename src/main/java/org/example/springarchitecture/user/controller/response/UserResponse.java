package org.example.springarchitecture.user.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.example.springarchitecture.user.domain.UserStatus;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;
}
