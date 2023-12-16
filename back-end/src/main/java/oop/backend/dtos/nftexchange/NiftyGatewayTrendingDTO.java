package oop.backend.dtos.nftexchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NiftyGatewayTrendingDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("líkes")
    private String likes;

    @JsonProperty("flooPrice")
    private String floorPrice;

    @JsonProperty("edition")
    private String edition;

    @JsonProperty("creator")
    private String creator;
}
