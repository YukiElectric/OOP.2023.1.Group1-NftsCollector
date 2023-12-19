package oop.backend.dtos.eplatform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import oop.backend.dtos.BaseDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BinanceDTO extends BaseDTO {
    @JsonProperty("volumeChange")
    private String volumeChange;

    @JsonProperty("floorChange")
    private String floorChange;
}

