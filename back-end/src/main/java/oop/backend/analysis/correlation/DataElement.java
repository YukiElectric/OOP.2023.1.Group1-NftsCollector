package oop.backend.analysis.correlation;


/** Lớp này sẽ trả về các đối tượng tương quan chứa thông tin về (tên nft; volume ; số lượng bài viết) **/

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataElement {
    private String marketplace;
    private String name;
    private double volume;                      // thông tin về volume của nft trên sàn tương ứng
    private String currency;                    // loại tiền tệ (ETH/BNB/USD)
    private List<String> contentList;           // thông tin về content các post về nft này trên twitter
}