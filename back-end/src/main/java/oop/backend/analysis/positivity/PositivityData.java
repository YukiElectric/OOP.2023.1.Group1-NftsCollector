package oop.backend.analysis.positivity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PositivityData {
    private String collection;
    private int positive;
    private int negative;
    private int neutral;
}
