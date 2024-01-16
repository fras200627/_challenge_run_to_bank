package com.challenge.branchs.service;

import com.challenge.branchs.domain.BranchRecord;
import com.challenge.branchs.domain.BranchRecordCreate;
import com.challenge.branchs.domain.BranchRecordUpdate;
import com.challenge.branchs.handler.exception.BadRequestException;
import com.challenge.branchs.model.BranchEntity;
import com.challenge.branchs.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

@Slf4j
@Service
public class BranchService {

    private final static String NAMEMODULE = "Branch";

    @Autowired private
    BranchRepository repository;

    private BranchEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(NAMEMODULE + " com id= '" + id + "' não encontrado!"));
    }
   
    @Transactional
    public BranchRecord save(BranchRecordCreate request,
                             Jwt jwt,
                             HttpServletRequest httpServletRequest) {
        
        BranchEntity result = repository.findByBranchName(request.branchName());
        if (result != null) {
            throw new BadRequestException(NAMEMODULE + " Name='" + request.branchName() + "' " +
                    "já existe. Verifique e tente novamente!");
        }
        BranchEntity regCreate = new BranchEntity(request);
        regCreate.setCreateAt(new Date());
        regCreate.setModifyAt(new Date());
        regCreate.setUserCode(jwt.getClaims().get("sub").toString());

        BranchRecord newBranch = new BranchRecord(repository.saveAndFlush(regCreate));
        
        return newBranch;
    }
   
    @Transactional
    public BranchRecord update(BranchRecordUpdate request,
                               Jwt jwt,
                               HttpServletRequest httpServletRequest) {

        BranchEntity regUpdate= this.findById(request.branchId());

        if (request.branchName() != null) {
            if (!request.branchName().trim().equalsIgnoreCase(regUpdate.getBranchName().trim())) {
                BranchEntity result = repository.findByBranchName(request.branchName());
                if (result != null && result.getBranchId() != request.branchId()) {
                    throw new BadRequestException(NAMEMODULE+ " Name='" + request.branchName() + "' " +
                            "já existe em um outro registro. Verifique e tente novamente!");
                }
            }
            regUpdate.setBranchName(request.branchName().trim().toUpperCase());
        }
        if (request.branchCity() != null) {
            regUpdate.setBranchCity(request.branchCity().trim().toUpperCase());
        }
        if (request.branchState() != null) {
            regUpdate.setBranchState(request.branchState().trim().toUpperCase());
        }

        regUpdate.setModifyAt(new Date());
        regUpdate.setUserCode(jwt.getClaims().get("sub").toString());
        
        BranchRecord updateBranch = new BranchRecord(repository.saveAndFlush(regUpdate));

        return updateBranch;
    }
    
}
