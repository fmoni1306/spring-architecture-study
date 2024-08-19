package org.example.springarchitecture.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
public class PostCreateDto {

    private final long writerId;
    private final String content;

    @Builder
    public PostCreateDto(
            @JsonProperty("writerId") long writerId,
            @JsonProperty("content") String content) {
        this.writerId = writerId;
        this.content = content;
    }
}
