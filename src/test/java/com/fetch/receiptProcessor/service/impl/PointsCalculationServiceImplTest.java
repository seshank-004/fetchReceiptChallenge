package com.fetch.receiptProcessor.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fetch.receiptProcessor.model.Receipt;

public class PointsCalculationServiceImplTest {
     private PointsCalculationServiceImpl pointsCalculationService;

    @BeforeEach
    public void setUp() {
        pointsCalculationService = new PointsCalculationServiceImpl();
    }

   
    @Test
    public void testCalculateAlphanumericPointWithSpecialCharacters() {
        String input = "Hello@World#123!";
        int count = pointsCalculationService.calculateAlphanumericPoint(input);
        assertEquals(13, count);
    }

    @Test
    public void testCalculateAlphanumericPointWithEmptyString() {
        String input = "";
        int count = pointsCalculationService.calculateAlphanumericPoint(input);
        assertEquals(0, count);
    }

    @Test
    public void testCalculateAlphanumericPointWithOnlySpecialCharacters() {
        String input = "!@#$%^&*()";
        int count = pointsCalculationService.calculateAlphanumericPoint(input);
        assertEquals(0, count);
    }
    @Test
    public void testCalculateRoundedDollarAmountWithNoCentsPositive() {
        double total = 10.0;
        int points = pointsCalculationService.calculateRoundedDollarAmountWithNoCents(total);
        assertEquals(50, points);
    }

    @Test
    public void testCalculateRoundedDollarAmountWithNoCentsNegative() {
        double total = 10.49;
        int points = pointsCalculationService.calculateRoundedDollarAmountWithNoCents(total);
        assertEquals(0, points);
    }

    @Test
    public void testCalculateRoundedDollarAmountWithNoCentsZero() {
        double total = 0.0;
        int points = pointsCalculationService.calculateRoundedDollarAmountWithNoCents(total);
        assertEquals(50, points);
    }

    @Test
    public void testCalculateMultipleOf025Positive() {
        double total = 0.75;
        int points = pointsCalculationService.calculateMultipleOf025(total);
        assertEquals(25, points);
    }

    @Test
    public void testCalculateMultipleOf025WholeNumber() {
        double total = 1.00;
        int points = pointsCalculationService.calculateMultipleOf025(total);
        assertEquals(25, points);
    }

    @Test
    public void testCalculateMultipleOf025Negative() {
        double total = 1.05;
        int points = pointsCalculationService.calculateMultipleOf025(total);
        assertEquals(0, points);
    }

    @Test
    public void testCalculateMultipleOf025Zero() {
        double total = 0.0;
        int points = pointsCalculationService.calculateMultipleOf025(total);
        assertEquals(25, points);
    }

    @Test
    public void testCalculateEveryTwoItemsOnTheReceiptSizeOddNumber() {
        int size = 5;
        int points = pointsCalculationService.calculateEveryTwoItemsOnTheReceipt(size);
        assertEquals(10, points);
    }

    @Test
    public void testCalculateEveryTwoItemsOnTheReceiptSizeEvenNumber() {
        int size = 10;
        int points = pointsCalculationService.calculateEveryTwoItemsOnTheReceipt(size);
        assertEquals(25, points);
    }

    @Test
    public void testCalculateEveryTwoItemsOnTheReceiptZero() {
        int size = 0;
        int points = pointsCalculationService.calculateEveryTwoItemsOnTheReceipt(size);
        assertEquals(0, points);
    }


    @Test
    public void testCalculatePoints() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("10.48");
        receipt.setItems(Arrays.asList(
            new Receipt.Item("Mountain Dew 12PK", "6.49"),
            new Receipt.Item("Doritos", "3.99")
        ));

        int points = pointsCalculationService.calculatePoints(receipt);
      
        assertEquals(17, points);
    }

    @Test
    public void testCalculatePointsWithDifferentRetailer() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Walmart");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("10.48");
        receipt.setItems(Arrays.asList(
            new Receipt.Item("Mountain Dew 12PK", "6.49"),
            new Receipt.Item("Doritos", "3.99")
        ));

        int points = pointsCalculationService.calculatePoints(receipt);
      
        assertEquals(18, points);
    }
    @Test
    public void testCalculatePointsWithDifferentDate() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-02");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("10.48");
        receipt.setItems(Arrays.asList(
            new Receipt.Item("Mountain Dew 12PK", "6.49"),
            new Receipt.Item("Doritos", "3.99")
        ));

        int points = pointsCalculationService.calculatePoints(receipt);
      
        assertEquals(11, points);
    }
    @Test
    public void testCalculatePointsWithDifferentTime() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("20:01");
        receipt.setTotal("10.48");
        receipt.setItems(Arrays.asList(
            new Receipt.Item("Mountain Dew 12PK", "6.49"),
            new Receipt.Item("Doritos", "3.99")
        ));

        int points = pointsCalculationService.calculatePoints(receipt);
      
        assertEquals(17, points);
    }
    @Test
    public void testCalculatePointsWithDifferentTotal() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("20.48");
        receipt.setItems(Arrays.asList(
            new Receipt.Item("Mountain Dew 12PK", "6.49"),
            new Receipt.Item("Doritos", "3.99")
        ));

        int points = pointsCalculationService.calculatePoints(receipt);
      
        assertEquals(17, points);
    }

}
