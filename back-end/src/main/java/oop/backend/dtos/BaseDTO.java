package oop.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO {
    @JsonProperty("img")
    private String img;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("volume")
    private String volume;
    
    @JsonProperty("floor_price")
    private String floor_price;
}

