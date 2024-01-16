package com.challenge.branchs.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record BranchRecordCreate(
        @NotNull @NotBlank @Size(min = 3, max = 100)
        String branchName,

        @NotNull @NotBlank @Size(min = 3, max = 100)
        String branchCity,

        @NotNull @NotBlank @Size(min = 2, max = 2)
        String branchState    
) {
}
