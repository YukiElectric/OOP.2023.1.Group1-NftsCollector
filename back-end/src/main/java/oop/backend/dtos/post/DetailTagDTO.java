package oop.backend.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailTagDTO {
    @JsonProperty("tag_detail")
    private String tagDetail;
    
    @JsonProperty("percent")
    private String percent;
}
