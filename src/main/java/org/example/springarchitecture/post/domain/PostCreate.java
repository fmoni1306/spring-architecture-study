package org.example.springarchitecture.post.domain;

import lombok.Builder;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
public class PostCreate {

    private final long writerId;
    private final String content;

    @Builder
    public PostCreate(
            @JsonProperty("writerId") long writerId,
            @JsonProperty("content") String content) {
        this.writerId = writerId;
        this.content = content;
    }
}
