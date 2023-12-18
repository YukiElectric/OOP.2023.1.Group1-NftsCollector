package oop.backend.dtos.eplatform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oop.backend.dtos.BaseDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaribleDTO extends BaseDTO {
    @JsonProperty("items")
    private String items;

    @JsonProperty("owners")
    private String owners;

    @JsonProperty("volumeChange")
    private String volumeChange;

    @JsonProperty("floorChange")
    private String floorChange;
}
