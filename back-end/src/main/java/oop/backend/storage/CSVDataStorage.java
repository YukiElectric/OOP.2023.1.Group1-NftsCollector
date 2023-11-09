package oop.backend.storage;

import oop.backend.Binance;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVDataStorage {
    private final String filePath;

    public CSVDataStorage(String fileName) {
        this.filePath = "D:/Document/" + fileName + ".csv";
    }

    public void storeDataInCSVFile(List<Binance> binances) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        // Ghi tên cột CSV
        writer.append("Img,Name,Volume,FloorPrice\n");

        for (Binance binance : binances) {
            writer.append(binance.getImg() + ",");
            writer.append(binance.getName() + ",");
            writer.append(binance.getVolume() + ",");
            writer.append(binance.getFloorPrice() + "\n");
        }

        writer.close();
    }
}
