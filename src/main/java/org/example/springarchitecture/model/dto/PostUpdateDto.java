package org.example.springarchitecture.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
public class PostUpdateDto {

    private final String content;

    @JsonCreator
    @Builder
    public PostUpdateDto(
            @JsonProperty("content") String content) {
        this.content = content;
    }
}
