package oop.backend.analysis.utils;

import oop.backend.dtos.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/** Lớp này dùng để lọc dữ liệu lỗi (có các thuộc tính rỗng, lặp lại,...) **/

public class DataFilter {
    // Method để loại bỏ các đối tượng trùng lặp (dùng khi update hoặc lưu trữ data) dựa trên một thuộc tính
    public void filterDuplicated(List<BaseDTO> dataList) {
        List<String> uniqueNames = new ArrayList<>();
        List<BaseDTO> tempList = new ArrayList<>();

        for (BaseDTO o : dataList) {
            if (!uniqueNames.contains(o.getName())) {
                uniqueNames.add(o.getName());
                tempList.add(o);
            } else {
                tempList.remove(o);
                System.out.println(o.getName());
            }
        }
        dataList.clear();
        dataList.addAll(tempList);
    }

    // Method để loại bỏ các đối tượng mà thuộc tính quan trọng có giá trị rỗng (name, volume)
    public void filterEmpty(List<BaseDTO> dataList){
        List<BaseDTO> tempList = new ArrayList<>();
        for (BaseDTO o: dataList){
            if (o.getName() != null && o.getVolume() != null
                && !o.getName().isEmpty()){
                tempList.add(o);
            }
        }
        dataList.clear();
        dataList.addAll(tempList);
    }
}
