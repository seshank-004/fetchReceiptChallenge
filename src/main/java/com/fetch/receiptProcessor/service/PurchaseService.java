package com.fetch.receiptProcessor.service;

import com.fetch.receiptProcessor.model.Receipt;

public interface PurchaseService {
    String saveReceipt(Receipt receipt);
    Integer getPointsById(String id);
}
