package oop.backend.crawler;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
/*
* Interface cho các class lấy data
* */
public interface DataCrawler<T> {
    List<T> getData( String request) throws Exception;
}
