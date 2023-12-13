package oop.backend.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwitterDTO {
    @JsonProperty("user")
    private String user;
    @JsonProperty("content")
    private String content;
    @JsonProperty("hashtag")
    private String hashtag;
    @JsonProperty("image")
    private String image;
}

