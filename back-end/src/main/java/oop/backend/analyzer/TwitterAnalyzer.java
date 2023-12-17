package oop.backend.analyzer;

import oop.backend.dtos.post.TwitterDTO;

import java.util.Hashtable;
import java.util.List;

// Lớp này tạo một bảng hash để gán các hashtag với số lần xuất hiện của chúng

public class TwitterAnalyzer {
    public static Hashtable<String, Integer> countHashtag(List<TwitterDTO> dataList){
        Hashtable<String, Integer> hashtagDict = new Hashtable<>();    //Tạo một bảng để lưu trữ các hashtag cùng với số lần xuất hiện
        for (TwitterDTO o : dataList) {
            String tmpHashtag = o.getHashtag();
            if (tmpHashtag != null) {
                if (hashtagDict.containsKey(tmpHashtag)) {
                    int count = hashtagDict.get(tmpHashtag);
                    hashtagDict.put(tmpHashtag, count + 1);
                } else {
                    hashtagDict.put(tmpHashtag, 1);
                }
            }
        }

        return hashtagDict;
    }
}
