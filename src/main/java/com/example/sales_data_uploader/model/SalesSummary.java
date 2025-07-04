package com.example.sales_data_uploader.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SalesSummary {
    private String id = UUID.randomUUID().toString();
    private LocalDateTime uploadTimestamp = LocalDateTime.now();
    private int totalRecords;
    private int totalQuantity;
    private double totalRevenue;
}
