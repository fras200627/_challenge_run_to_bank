package com.challenge.branchs.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity(name= "Branchs")
@Table(schema= "CHALLENGE", name= "TB_BRANCHS")
//---------------------------------------------------
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BranchEntity {

    @Id
    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "BRANCH_CITY")
    private String branchCity;

    @Column(name = "BRANCH_STATE")
    private String branchState;

    @Column(name = "CREATE_AT")
    private Date createAt;

    @Column(name = "MODIFY_AT")
    private Date modifyAt;

    @Column(name = "USER_CODE")
    private String userCode;
    
}
