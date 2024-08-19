package org.example.springarchitecture.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.springarchitecture.model.UserStatus;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;
}
