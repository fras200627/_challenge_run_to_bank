package com.challenge.customers.domain;

import org.springframework.lang.Nullable;
import javax.validation.constraints.*;

public record CustomerRecordUpdate(

        @NotNull 
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long customerId,

        @NotNull @NotBlank @Size(min = 10, max = 100)
        String customerName,

        @NotNull @NotBlank @Size(min = 20, max = 300)
        String customerAddress
        
) {
}
