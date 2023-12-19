package oop.backend.dtos.eplatform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NiftyTrendingDTO {
    @JsonProperty("collection")
    private String collection;

    @JsonProperty("likes")
    private String likes;

    @JsonProperty("flooPrice")
    private String floorPrice;

    @JsonProperty("edition")
    private String edition;

    @JsonProperty("creator")
    private String creator;
}
