package oop.backend;

import oop.backend.analyzer.DataCorrelation;
import oop.backend.analyzer.DataFilter;
import oop.backend.storage.CSVDataStorage;
import oop.backend.storage.JSONDataStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);


        UseGsonAndSpringBoot useGsonAndSpringBoot = new UseGsonAndSpringBoot();
        List<Binance> binanceData = useGsonAndSpringBoot.getBinaceData();

        // Test filter
        DataFilter dataFilter = new DataFilter();
        dataFilter.filterDuplicated(binanceData);

        // Test lưu dữ liệu vào tệp JSON và CSV
        JSONDataStorage jsonDataStorage = new JSONDataStorage("binance_data");
        jsonDataStorage.storeDataInJsonFile(binanceData);

        CSVDataStorage csv = new CSVDataStorage("binance");
        csv.storeDataInCSVFile(binanceData);

        //TODO: list twitterData = new getDataFromTwitter()

        // Test DataCorrelation
        for (Binance o : binanceData){
            String name = o.getName();

            DataCorrelation item = new DataCorrelation();
        }

    }
}
