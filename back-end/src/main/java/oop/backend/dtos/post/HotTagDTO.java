package oop.backend.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oop.backend.dtos.post.DetailTagDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotTagDTO {
    @JsonProperty("tag")
    private String tag;
    
    @JsonProperty("total_post")
    private int totalPost;
    
    @JsonProperty("detail_tag")
    private List<DetailTagDTO> detailTag;
}
