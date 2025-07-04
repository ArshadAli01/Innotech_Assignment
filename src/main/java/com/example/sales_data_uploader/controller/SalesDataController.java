package com.example.sales_data_uploader.controller;

import com.example.sales_data_uploader.model.SalesSummary;
import com.example.sales_data_uploader.service.SalesDataService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SalesDataController {

    private final SalesDataService service;

    public SalesDataController(SalesDataService service) {
        this.service = service;
    }

    @PostMapping("/upload-sales-data")
    public ResponseEntity<?> uploadSalesData(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No file uploaded");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            SalesSummary summary = service.processCsvFile(file);
            return ResponseEntity.ok(summary);
        } catch (IOException | CsvValidationException | NumberFormatException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error processing file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/sales-summaries")
    public ResponseEntity<List<SalesSummary>> getSalesSummaries() {
        return ResponseEntity.ok(service.getAllSummaries());
    }
}