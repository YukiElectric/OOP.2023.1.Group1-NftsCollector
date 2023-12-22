package oop.backend.analysis.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketplaceStatistics {
    private String marketplace;
    private double pearson;
    private double spearman;
}
