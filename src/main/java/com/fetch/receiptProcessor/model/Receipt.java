package com.fetch.receiptProcessor.model;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class Receipt {
    @NotBlank
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private String retailer;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String purchaseDate;

    @NotBlank
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String purchaseTime;

    @NotNull
    @Size(min = 1)
    private List<@Valid Item> items;

    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total;

    @Data
    public static class Item {
        @NotBlank
        @Pattern(regexp = "^[\\w\\s\\-]+$")
        private String shortDescription;

        @NotBlank
        @Pattern(regexp = "^\\d+\\.\\d{2}$")
        private String price;
        public Item(String shortDescription, String price) {
            this.shortDescription = shortDescription;
            this.price = price;
        }
    }
}

