package com.example.sales_data_uploader.service;

import com.example.sales_data_uploader.model.SalesRecord;
import com.example.sales_data_uploader.model.SalesSummary;
import com.example.sales_data_uploader.repository.InMemorySalesRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesDataService {

    private final InMemorySalesRepository repository;

    public SalesDataService(InMemorySalesRepository repository) {
        this.repository = repository;
    }

    public SalesSummary processCsvFile(MultipartFile file) throws IOException, CsvValidationException {
        List<SalesRecord> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            csvReader.readNext(); // Skip header
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line.length == 3) {
                    SalesRecord record = new SalesRecord();
                    record.setProductName(line[0]);
                    record.setQuantity(Integer.parseInt(line[1]));
                    record.setPricePerUnit(Double.parseDouble(line[2]));
                    records.add(record);
                }
            }
        }

        SalesSummary summary = new SalesSummary();
        summary.setTotalRecords(records.size());
        summary.setTotalQuantity(records.stream().mapToInt(SalesRecord::getQuantity).sum());
        summary.setTotalRevenue(records.stream().mapToDouble(r -> r.getQuantity() * r.getPricePerUnit()).sum());
        repository.save(summary);
        return summary;
    }

    public List<SalesSummary> getAllSummaries() {
        return repository.getAll();
    }
}