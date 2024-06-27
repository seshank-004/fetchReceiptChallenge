package com.fetch.receiptProcessor.service;

import com.fetch.receiptProcessor.model.Receipt;

public interface PointsCalculationService {
    int calculatePoints(Receipt receipt);
}
