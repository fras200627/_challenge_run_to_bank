package com.challenge.customers.domain;

import javax.validation.constraints.*;

public record CustomerRecordStatusUpdate(

        @NotNull 
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long customerId,

        @NotNull @Pattern(regexp = "ATIVO|INATIVO|SUSPENSO|CANCELADO|BLOQUEADO")
        String customerStatus
        
) {
}
