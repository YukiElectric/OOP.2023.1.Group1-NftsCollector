package oop.backend.attributesgetter;

import org.jsoup.nodes.Element;
/*
* Đây là interface cho các phương thức lấy attributes từ elements
* */
public interface AttrGetter<T> {
    T attrGet(Element element);
}
