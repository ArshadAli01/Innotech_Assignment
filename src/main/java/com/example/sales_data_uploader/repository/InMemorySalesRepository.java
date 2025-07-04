package com.example.sales_data_uploader.repository;


import com.example.sales_data_uploader.model.SalesSummary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemorySalesRepository {
    private final List<SalesSummary> summaries = new ArrayList<>();

    public void save(SalesSummary summary) {
        summaries.add(summary);
    }

    public List<SalesSummary> getAll() {
        return new ArrayList<>(summaries);
    }
}
