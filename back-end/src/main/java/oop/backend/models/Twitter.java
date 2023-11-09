package oop.backend.models;

import lombok.*;

@Data  //Giúp thực hiện hàm toString trong model
@Getter // Hàm Getter
@Setter // Hàm Setter
@AllArgsConstructor //Hàm Constructor có tham số
@NoArgsConstructor  //Hàm Constructor không tham số
public class Twitter {
    private String user;
    private String content;
    private String hashtag;
    private String image;
}