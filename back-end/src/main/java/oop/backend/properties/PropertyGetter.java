package oop.backend.properties;

import org.jsoup.nodes.Element;
/*
* Đây là interface cho các phương thức lấy attributes từ elements
* */
public interface PropertyGetter<T> {
    T attrGet(Element element);
}
