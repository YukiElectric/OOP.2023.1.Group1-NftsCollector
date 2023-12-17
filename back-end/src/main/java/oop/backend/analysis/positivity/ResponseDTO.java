package oop.backend.analysis.positivity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private List<Choice> choices;
    @Getter
    public static class Choice {
        private Message message;
        @Getter
        public static class Message{
            private String content;
        }
    }
}
