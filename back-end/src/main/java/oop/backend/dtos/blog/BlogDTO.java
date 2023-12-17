package oop.backend.dtos.blog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    @JsonProperty("linkBlog")
    private String linkBlog;
    
    @JsonProperty("image")
    private String image;
    
    @JsonProperty("label")
    private List<String> label;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("time")
    private String time;
    
    @JsonProperty("article")
    private String article;
}
