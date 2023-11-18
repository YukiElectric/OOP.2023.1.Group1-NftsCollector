package oop.backend.datacollection;

import java.util.List;
/*
* Interface cho các class lấy data
* */
public interface DataCrawler<T> {
    List<T> getData() throws Exception;
}
