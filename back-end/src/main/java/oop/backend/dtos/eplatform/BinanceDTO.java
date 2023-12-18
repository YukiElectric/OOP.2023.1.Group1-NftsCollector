package oop.backend.dtos.eplatform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import oop.backend.dtos.BaseDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BinanceDTO extends BaseDTO {
    @JsonProperty("items")
    private String items;

    @JsonProperty("owners")
    private String owners;

    @JsonProperty("volumeChange")
    private String volumeChange;

    @JsonProperty("floorChange")
    private String floorChange;
}

