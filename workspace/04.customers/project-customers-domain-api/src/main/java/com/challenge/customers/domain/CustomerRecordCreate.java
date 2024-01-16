package com.challenge.customers.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record CustomerRecordCreate(
        
        @NotNull @Pattern(regexp = "F|J")
        String customerType,

        @NotNull @NotBlank @Size(min = 3, max = 18)
        String customerDocument,

        @NotNull @NotBlank @Size(min = 10, max = 100)
        String customerName,

        @NotNull @NotBlank @Size(min = 20, max = 300)
        String customerAddress,

        @NotNull @NotBlank @Size(min = 6, max = 20)
        String customerPassword

) {
}
