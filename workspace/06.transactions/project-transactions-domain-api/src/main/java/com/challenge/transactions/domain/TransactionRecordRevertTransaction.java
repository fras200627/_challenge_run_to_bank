package com.challenge.transactions.domain;

import javax.validation.constraints.*;

public record TransactionRecordRevertTransaction(

        @NotNull
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long transactionId,
        
        @NotNull @NotBlank @Size(min = 3, max = 50)
        String transactionDescription
        
) {
}
