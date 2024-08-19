package org.example.springarchitecture.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
public class UserUpdate {

    private final String nickname;
    private final String address;

    @Builder
    public UserUpdate(
            @JsonProperty("nickname") String nickname,
            @JsonProperty("address") String address) {
        this.nickname = nickname;
        this.address = address;
    }
}
