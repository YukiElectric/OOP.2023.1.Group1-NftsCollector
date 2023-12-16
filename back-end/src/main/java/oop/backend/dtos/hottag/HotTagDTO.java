package oop.backend.dtos.hottag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotTagDTO {
    @JsonProperty("has_tag")
    private String hasTag;
    
    @JsonProperty("freq")
    private int freq;

}