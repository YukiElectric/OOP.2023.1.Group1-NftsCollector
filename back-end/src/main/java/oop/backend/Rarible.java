package oop.backend;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rarible {
    private String collection;
    private String floorPrice;
    private String volume;
    private String owner;
    private String image;
}

