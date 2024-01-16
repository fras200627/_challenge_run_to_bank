package com.challenge.branchs.model;

import com.challenge.branchs.domain.BranchRecordCreate;
import com.challenge.branchs.domain.BranchRecordUpdate;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.*;
import java.util.Date;

@Entity(name= "Branchs")
@Table(schema= "CHALLENGE", name= "TB_BRANCHS")
//---------------------------------------------------
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of= "branchId")
@Getter @Setter
public class BranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BRANCHS")
    @SequenceGenerator(schema = "CHALLENGE", name = "SEQ_BRANCHS", allocationSize = 1, sequenceName = "SEQ_BRANCHS")
    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "BRANCH_NAME", length = 100, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String branchName;

    @Column(name = "BRANCH_CITY", length = 100, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String branchCity;

    @Column(name = "BRANCH_STATE", length = 2, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String branchState;

    @Column(name = "CREATE_AT", nullable = true)
    private Date createAt;

    @Column(name = "MODIFY_AT", nullable = true)
    private Date modifyAt;

    @Column(name = "USER_CODE", length = 30, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String userCode;

    public BranchEntity(BranchRecordCreate request) {
        this.setBranchName(request.branchName().trim().toUpperCase());
        this.setBranchCity(request.branchCity().trim().toUpperCase());
        this.setBranchState(request.branchState().trim().toUpperCase());   
    }

    public BranchEntity(BranchRecordUpdate request) {
        this.setBranchId(request.branchId());
        this.setBranchName(request.branchName().trim().toUpperCase());
        this.setBranchCity(request.branchCity().trim().toUpperCase());
        this.setBranchState(request.branchState().trim().toUpperCase());
    }
}
