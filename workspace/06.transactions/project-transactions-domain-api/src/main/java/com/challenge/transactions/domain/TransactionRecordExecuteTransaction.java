package com.challenge.transactions.domain;

import javax.validation.constraints.*;

public record TransactionRecordExecuteTransaction(

        @NotNull @Pattern(regexp = "PIX|PAGTO")
        String transactionType,

        @NotNull @NotBlank @Size(min = 3, max = 50)
        String transactionDescription,

        @NotNull
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long accountIdOrigin,

        @NotNull
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long accountIdDestination,
        
        @NotNull
        @Digits(integer=10, fraction=2)
        @Min(value = 0)
        Double transactionValue
        
) {
}
