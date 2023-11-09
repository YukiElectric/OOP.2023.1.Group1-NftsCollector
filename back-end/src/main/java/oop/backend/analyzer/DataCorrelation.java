package oop.backend.analyzer;


// Lớp này sẽ trả về các đối tượng tương quan chứa thông tin về (tên nft; price/volume ; hashtag/số lượng bài viết)

import lombok.*;
@Data  //Giúp thực hiện hàm toString trong model
@Getter // Hàm Getter
@Setter // Hàm Setter
@AllArgsConstructor //Hàm Constructor có tham số
@NoArgsConstructor  //Hàm Constructor không tham số
public class DataCorrelation {
    private String name;
    private int nftData;             // thông tin của một loại nft trên sàn nft (price/volume)
    private int tweetData;           // thông tin của một loại nft trên twitter (số lượng hashtag/post liên quan đến nft đó)

}