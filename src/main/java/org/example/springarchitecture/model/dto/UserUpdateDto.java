package org.example.springarchitecture.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
public class UserUpdateDto {

    private final String nickname;
    private final String address;

    @Builder
    public UserUpdateDto(
            @JsonProperty("nickname") String nickname,
            @JsonProperty("address") String address) {
        this.nickname = nickname;
        this.address = address;
    }
}
