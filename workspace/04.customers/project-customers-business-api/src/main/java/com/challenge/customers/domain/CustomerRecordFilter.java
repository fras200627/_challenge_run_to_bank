package com.challenge.customers.domain;

import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.*;

public record CustomerRecordFilter(
        @Valid @NotNull
        CustomerPaginationSettings page_settings,

        @Nullable
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long customerId,
        
        @Nullable 
        @Pattern(regexp = "F|J") 
        String customerType,

        @Nullable 
        @Pattern(regexp = "ATIVO|INATIVO|SUSPENSO|CANCELADO|BLOQUEADO")
        String customerStatus,
        
        @Nullable 
        @Size(min = 3, max = 18)
        String customerDocument,

        @Nullable 
        @Size(min = 10, max = 100)
        String customerName
        
) {
}
