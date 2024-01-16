package com.challenge.branchs.domain;

import org.springframework.lang.Nullable;
import javax.validation.constraints.*;

public record BranchRecordUpdate(

        @NotNull 
        @Digits(integer=10, fraction=0)
        @Min(value = 1500)
        Long branchId,

        @Nullable 
        @Size(min = 3, max = 100)
        String branchName,

        @Nullable 
        @Size(min = 3, max = 100)
        String branchCity,

        @Nullable @Size(min = 2, max = 2)
        String branchState    

) {
}
