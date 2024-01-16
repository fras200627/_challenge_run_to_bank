package com.challenge.accounts.domain;

import javax.validation.constraints.*;

public record AccountRecordStatusUpdate(

        @NotNull 
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long accountId,

        @NotNull @Pattern(regexp = "ATIVA|INATIVA|SUSPENSA|CANCELADA|BLOQUEADA")
        String accountStatus
       
) {
}
