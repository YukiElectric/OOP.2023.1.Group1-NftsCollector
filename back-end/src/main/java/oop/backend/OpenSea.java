package oop.backend;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenSea {
    private String name;
    private String rarityRank;
    private String price;
    private String image;
}
