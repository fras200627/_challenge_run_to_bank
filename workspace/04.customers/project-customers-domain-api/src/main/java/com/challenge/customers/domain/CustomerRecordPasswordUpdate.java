package com.challenge.customers.domain;

import javax.validation.constraints.*;

public record CustomerRecordPasswordUpdate(

        @NotNull 
        @Digits(integer=10, fraction=0)
        @Min(value = 1)
        Long customerId,

        @NotNull @NotBlank @Size(min = 6, max = 20)
        String customerOldPassword,

        @NotNull @NotBlank @Size(min = 6, max = 20)
        String customerNewPassword
        
) {
}
