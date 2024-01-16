package com.challenge.accounts.domain;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record AccountPaginationSettings(
        @NotNull @Digits(integer = 5, fraction = 0)
        @Range(min = 1, max = 99999)
        int page_number,

        @NotNull @Digits(integer = 4, fraction = 0)
        @Range(min = 1, max = 5000)
        int page_size,

        @Nullable @Pattern(regexp = "accountId|accountNumber|accountStatus|branchId|customerId")
        String page_sort_field,

        @Nullable @Pattern(regexp = "ASC|DESC")
        String page_sort_order
) {
    public static Pageable PaginationSettingsTemplate(AccountPaginationSettings pageSettings, String fixField) {
        return PageRequest
                .of(pageSettings.page_number()-1, pageSettings.page_size())
                .withSort(
                        pageSettings.page_sort_order()==null ? Sort.Direction.ASC
                                :
                                (pageSettings.page_sort_order().equals("ASC") ? Sort.Direction.ASC
                                        : Sort.Direction.DESC),
                        pageSettings.page_sort_field()==null ? fixField.trim()
                                :
                                pageSettings.page_sort_field()
                );
    }
}
