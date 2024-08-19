package org.example.springarchitecture.post.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
public class PostUpdate {

    private final String content;

    @JsonCreator
    @Builder
    public PostUpdate(
            @JsonProperty("content") String content) {
        this.content = content;
    }
}
