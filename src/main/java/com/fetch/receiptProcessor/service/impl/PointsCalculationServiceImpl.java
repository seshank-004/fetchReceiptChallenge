package com.fetch.receiptProcessor.service.impl;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.service.PointsCalculationService;
import com.fetch.receiptProcessor.utils.Constants;


@Service
public class PointsCalculationServiceImpl implements PointsCalculationService {
   

    public int calculateAlphanumericPoint(String retailerName) {
        int count = 0;
        for (int i = 0; i < retailerName.length(); i++) {
            char ch = retailerName.charAt(i);
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                count++;
            }
        }
        return count * Constants.ALPHANUMERIC_POINT;
    }

    public int calculateRoundedDollarAmountWithNoCents(double total) {
        return total % 1 == 0 ? Constants.ROUND_DOLLAR_POINTS : 0;
    }

    public int calculateMultipleOf025(double total) {
        return total % 0.25 == 0 ? Constants.MULTIPLE_OF_025_POINTS : 0;
    }

    public int calculateEveryTwoItemsOnTheReceipt(int size) {
        return (size / 2) * Constants.EVERY_TWO_ITEMS_POINTS;
    }

    public int calculateLengthOfTheItemDescription(Receipt receipt) {
        int total = 0;
        for (Receipt.Item item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                total += (int) Math.ceil(Double.parseDouble(item.getPrice()) * Constants.LENGTH_OF_ITEM_DESCRIPTION_POINTS);
            }
        }
        return total;
    }

    public int calculateOnPurchaseDate(String purchaseDate) {
        LocalDate date = LocalDate.parse(purchaseDate, DateTimeFormatter.ISO_DATE);
        return date.getDayOfMonth() % 2 == 1 ? Constants.ODD_DATE_POINTS : 0;
    }

    public int calculateTimeOfPurchase(String purchaseTime) {
        LocalTime startTime = LocalTime.parse(Constants.START_TIME);
        LocalTime stopTime = LocalTime.parse(Constants.STOP_TIME);
        LocalTime time = LocalTime.parse(purchaseTime);
        return (time.isAfter(startTime) && time.isBefore(stopTime)) ? Constants.TIME_OF_PURCHASE_POINTS : 0;
    }

    @Override
    public int calculatePoints(Receipt receipt) {
        int totalPoints = 0;
        totalPoints += calculateAlphanumericPoint(receipt.getRetailer());
        totalPoints += calculateRoundedDollarAmountWithNoCents(Double.parseDouble(receipt.getTotal()));
        totalPoints += calculateMultipleOf025(Double.parseDouble(receipt.getTotal()));
        totalPoints += calculateEveryTwoItemsOnTheReceipt(receipt.getItems().size());
        totalPoints += calculateLengthOfTheItemDescription(receipt);
        totalPoints += calculateOnPurchaseDate(receipt.getPurchaseDate());
        totalPoints += calculateTimeOfPurchase(receipt.getPurchaseTime());
        return totalPoints;
    }
}

