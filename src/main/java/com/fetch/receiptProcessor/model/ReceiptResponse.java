package com.fetch.receiptProcessor.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ReceiptResponse {
    @NotBlank
    @Pattern(regexp = "^\\S+$")
    private String id;
}
