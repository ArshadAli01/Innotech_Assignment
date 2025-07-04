package com.example.sales_data_uploader.model;

import lombok.Data;

@Data
public class SalesRecord {
    private String productName;
    private int quantity;
    private double pricePerUnit;
}
