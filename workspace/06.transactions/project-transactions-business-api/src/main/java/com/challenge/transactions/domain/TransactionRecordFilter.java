package com.challenge.transactions.domain;

import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.*;

public record TransactionRecordFilter(
        @Valid @NotNull
        TransactionPaginationSettings page_settings,
        
        @Nullable
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long transactionId,

        @Nullable
        @Pattern(regexp = "EFETIVADA|ESTORNADA")
        String transactionStatus,

        @Nullable
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long accountIdOrigin,

        @Nullable
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long accountIdDestination
) {
}
