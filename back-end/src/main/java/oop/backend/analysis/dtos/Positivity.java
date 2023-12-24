package oop.backend.analysis.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Positivity {
    private String collection;
    private int positive;
    private int negative;
    private int neutral;
}
