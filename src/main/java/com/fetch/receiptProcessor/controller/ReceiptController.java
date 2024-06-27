package com.fetch.receiptProcessor.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.receiptProcessor.model.PointsResponse;
import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.model.ReceiptResponse;
import com.fetch.receiptProcessor.service.PurchaseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;


@RestController
@RequestMapping("/receipts")
@Validated
public class ReceiptController {
    private final PurchaseService purchaseService;

    public ReceiptController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/process")
    public ReceiptResponse processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = purchaseService.saveReceipt(receipt);
        return new ReceiptResponse(id);
    }

    @GetMapping("/{id}/points")
    public PointsResponse getPoints(@PathVariable @Pattern(regexp = "^\\S+$") String id) {
        Integer points = purchaseService.getPointsById(id);
        return new PointsResponse(points != null ? points : 0);
    }
}
