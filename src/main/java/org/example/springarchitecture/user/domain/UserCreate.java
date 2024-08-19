package org.example.springarchitecture.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
public class UserCreate {

    private final String email;
    private final String nickname;
    private final String address;

    @Builder
    public UserCreate(
            @JsonProperty("email") String email,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("address") String address) {
        this.email = email;
        this.nickname = nickname;
        this.address = address;
    }
}
