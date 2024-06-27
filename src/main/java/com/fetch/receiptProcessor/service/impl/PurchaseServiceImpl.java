package com.fetch.receiptProcessor.service.impl;


import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.service.PointsCalculationService;
import com.fetch.receiptProcessor.service.PurchaseService;


@Service
public class PurchaseServiceImpl implements PurchaseService {
    

    private final Map<String, Integer> receiptStorage = new HashMap<>();
    private final PointsCalculationService pointsCalculationService;

    public PurchaseServiceImpl(PointsCalculationService pointsCalculationService) {
        this.pointsCalculationService = pointsCalculationService;
    }

    @Override
    public String saveReceipt(Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt cannot be null");
        }
        if (receipt.getRetailer() == null || receipt.getRetailer().isEmpty()) {
            throw new IllegalArgumentException("Retailer cannot be null or empty");
        }
        if (receipt.getPurchaseDate() == null || receipt.getPurchaseDate().isEmpty()) {
            throw new IllegalArgumentException("Purchase date cannot be null or empty");
        }
        if (receipt.getPurchaseTime() == null || receipt.getPurchaseTime().isEmpty()) {
            throw new IllegalArgumentException("Purchase time cannot be null or empty");
        }
        if (receipt.getItems() == null || receipt.getItems().isEmpty()) {
            throw new IllegalArgumentException("Items cannot be null or empty");
        }
        if (receipt.getTotal() == null || receipt.getTotal().isEmpty()) {
            throw new IllegalArgumentException("Total cannot be null or empty");
        }

        String id = UUID.randomUUID().toString();
        int points = pointsCalculationService.calculatePoints(receipt);
        receiptStorage.put(id, points);
        return id;
    }

    @Override
    public Integer getPointsById(String id) {
         if (!receiptStorage.containsKey(id)) {
            throw new NoSuchElementException("Receipt not found for ID: " + id);
        }
        return receiptStorage.get(id);
    }
}

