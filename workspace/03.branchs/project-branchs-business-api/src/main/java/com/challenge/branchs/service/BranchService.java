package com.challenge.branchs.service;

import com.challenge.branchs.domain.BranchRecord;
import com.challenge.branchs.jpa.RequestFilterParams;
import com.challenge.branchs.jpa.RequestFilterPredicatesEnum;
import com.challenge.branchs.jpa.RequestFilterSpecification;
import com.challenge.branchs.domain.BranchPaginationSettings;
import com.challenge.branchs.domain.BranchRecordFilter;
import com.challenge.branchs.handler.exception.BadRequestException;
import com.challenge.branchs.model.BranchEntity;
import com.challenge.branchs.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class BranchService {

    private final static String NAMEMODULE = "Branch";

    @Autowired private
    BranchRepository repository;
    
    public BranchRecord findById(Long id, Jwt jwt, HttpServletRequest httpRequest) {
        try {
            String clientId = jwt.getClaims().get("sub").toString();
            return new BranchRecord(repository.findById(id).get());
        } catch (Exception ex) {
            throw new BadRequestException(NAMEMODULE + " com Id= [" + id + "] não encontrado! Verifique e tente novamente.");
        }
    }

    public String existsBranch(Long id, Jwt jwt, HttpServletRequest httpRequest) {
        String clientId = jwt.getClaims().get("sub").toString();
        try {
            BranchRecord result = new BranchRecord(repository.findById(id).get());          
            return "true";
        } catch (Exception ex) {
            return "false";
        }
    }

    public List<BranchRecord> findAll(Jwt jwt,
                                      HttpServletRequest httpRequest) {

        String clientId = jwt.getClaims().get("sub").toString();
        
        return repository.findAll(Sort.by(Sort.Direction.ASC, "branchName"))
                         .stream()
                         .map(BranchRecord::new)
                         .toList();
    }

    public Page<BranchRecord> findByPageable(BranchPaginationSettings pageParams,
                                             Jwt jwt,
                                             HttpServletRequest httpRequest)  {

        String clientId = jwt.getClaims().get("sub").toString();

        return repository.findAll(BranchPaginationSettings
                         .PaginationSettingsTemplate(pageParams, "branchName"))
                         .map(BranchRecord::new);
    }

    public Page<BranchRecord> findByFilters(BranchRecordFilter filter,
                                            Jwt jwt,
                                            HttpServletRequest httpRequest) {

        String clientId = jwt.getClaims().get("sub").toString();
        
        Pageable pageParams = BranchPaginationSettings
                              .PaginationSettingsTemplate(filter.page_settings(), "branchName");
        
        Specification<BranchEntity> filterSpecs = this.buildFilter(filter);
        
        if (filterSpecs == null) {
            throw new BadRequestException("Não há parametros definidos para o Filtro. Verifique e tente novamente!");
        }
        
        return repository.findAll(filterSpecs, pageParams).map(BranchRecord::new);
    }

    public Specification<BranchEntity> buildFilter(BranchRecordFilter filter) {

        Specification<BranchEntity> specs = null;
        RequestFilterParams requestFilterParams = null;

        if (filter.branchId() != null) {

            requestFilterParams = new RequestFilterParams("branchId",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.branchId().toString(),
                    filter.branchId().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.branchName() != null
                && filter.branchName().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("branchName",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.branchName().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.branchCity() != null
                && filter.branchCity().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("branchCity",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.branchCity().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.branchState() != null
                && filter.branchState().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("branchState",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.branchState().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }
        
        return specs;
    }
}
