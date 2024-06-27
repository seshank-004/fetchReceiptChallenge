package com.fetch.receiptProcessor.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fetch.receiptProcessor.model.Receipt;

public class PurchaseServiceImplTest {

    private PurchaseServiceImpl purchaseService;
    private PointsCalculationServiceImpl pointsCalculationService;

    @BeforeEach
    public void setUp() {
        pointsCalculationService = mock(PointsCalculationServiceImpl.class);
        purchaseService = new PurchaseServiceImpl(pointsCalculationService);
    }

    @Test
    public void testSaveReceipt() {
       
        Receipt.Item item1 = new Receipt.Item("Mountain Dew 12PK", "6.49");
        Receipt.Item item2 = new Receipt.Item("Doritos", "3.99");
    
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setItems(Arrays.asList(item1, item2));
        receipt.setTotal("10.48");
    

        when(pointsCalculationService.calculatePoints(receipt)).thenReturn(19);

        String id = purchaseService.saveReceipt(receipt);
        Integer points = purchaseService.getPointsById(id);

        assertEquals(19, points);
        verify(pointsCalculationService, times(1)).calculatePoints(receipt);
    }
     @Test
    public void testSaveReceiptNull() {
        assertThrows(IllegalArgumentException.class, () -> purchaseService.saveReceipt(null));
    }

    @Test
    public void testGetPointsByIdInvalidId() {
        assertThrows(NoSuchElementException.class, () -> purchaseService.getPointsById("invalid-id"));
    }

    @Test
    public void testSaveReceiptWithMultipleItems() {
        Receipt.Item item1 = new Receipt.Item("Mountain Dew 12PK", "6.49");
        Receipt.Item item2 = new Receipt.Item("Doritos", "3.99");
        Receipt.Item item3 = new Receipt.Item("Pepsi", "5.99");

        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setItems(Arrays.asList(item1, item2, item3));
        receipt.setTotal("16.47");

        when(pointsCalculationService.calculatePoints(receipt)).thenReturn(25);

        String id = purchaseService.saveReceipt(receipt);
        Integer points = purchaseService.getPointsById(id);

        assertEquals(25, points);
        verify(pointsCalculationService, times(1)).calculatePoints(receipt);
    }

    @Test
    public void testSaveReceiptWithMissingFields() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");

        assertThrows(IllegalArgumentException.class, () -> purchaseService.saveReceipt(receipt));
    }

    @Test
    public void testSaveAndRetrievePointsMultipleReceipts() {
        Receipt.Item item1 = new Receipt.Item("Mountain Dew 12PK", "6.49");
        Receipt.Item item2 = new Receipt.Item("Doritos", "3.99");
    
        Receipt receipt1 = new Receipt();
        receipt1.setRetailer("Target");
        receipt1.setPurchaseDate("2022-01-01");
        receipt1.setPurchaseTime("13:01");
        receipt1.setItems(Arrays.asList(item1, item2));
        receipt1.setTotal("10.48");
    
        Receipt receipt2 = new Receipt();
        receipt2.setRetailer("Walmart");
        receipt2.setPurchaseDate("2022-01-02");
        receipt2.setPurchaseTime("14:30");
        receipt2.setItems(Arrays.asList(item1, item2));
        receipt2.setTotal("20.00");
    
        when(pointsCalculationService.calculatePoints(receipt1)).thenReturn(19);
        when(pointsCalculationService.calculatePoints(receipt2)).thenReturn(30);
    
        String id1 = purchaseService.saveReceipt(receipt1);
        String id2 = purchaseService.saveReceipt(receipt2);
    
        Integer points1 = purchaseService.getPointsById(id1);
        Integer points2 = purchaseService.getPointsById(id2);
    
        assertEquals(19, points1);
        assertEquals(30, points2);
    
        verify(pointsCalculationService, times(1)).calculatePoints(receipt1);
        verify(pointsCalculationService, times(1)).calculatePoints(receipt2);
    }

}
