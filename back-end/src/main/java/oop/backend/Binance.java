package oop.backend;

//Đây là 1 model dùng để test Lombok và ví dụ cho trả về Json

import lombok.*;

@Data  //Giúp thực hiện hàm toString trong model
@Getter // Hàm Getter
@Setter // Hàm Setter
@AllArgsConstructor //Hàm Constructor có tham số
@NoArgsConstructor  //Hàm Constructor không tham số
public class Binance {
    private String img;
    private String name;
    private String volume;
    private String floorPrice;
}
