package com.challenge.accounts.domain;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public record AccountRecordCreate(

        @NotNull
        @Digits(integer=10, fraction=0)
        @Min(value = 1000)
        Long branchId,

        @NotNull
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long customerId,

        @NotNull
        @Digits(integer=10, fraction=2)
        @Min(value = 0)
        Double accountBalance

) {
}
