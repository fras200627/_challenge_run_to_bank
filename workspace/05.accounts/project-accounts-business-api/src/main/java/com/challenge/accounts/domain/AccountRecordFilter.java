package com.challenge.accounts.domain;

import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.*;

public record AccountRecordFilter(
        @Valid @NotNull
        AccountPaginationSettings page_settings,

        @Nullable
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long accountId,

        @Nullable
        @Size(min = 11, max = 11)
        String accountNumber,
        
        @Nullable
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long branchId,
        
        @Nullable
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long customerId,
        
        @Nullable 
        @Pattern(regexp = "ATIVA|INATIVA|SUSPENSA|CANCELADA|BLOQUEADA")
        String accountStatus
        
) {
}
