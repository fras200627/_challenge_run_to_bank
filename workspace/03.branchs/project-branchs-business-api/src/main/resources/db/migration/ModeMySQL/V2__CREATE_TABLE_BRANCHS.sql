-- Create sequence
create sequence CHALLENGE.SEQ_BRANCHS
minvalue 1500
maxvalue 9999
start with 1503
increment by 1
cache 20;

create table CHALLENGE.TB_BRANCHS(
    branch_id     bigint       not null,
    branch_name   varchar(100) not null,
    branch_city   varchar(100) not null,
    branch_state  varchar(2),
    create_At     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_At     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_code     varchar2(20),
    primary       key(branch_id),
    UNIQUE        (branch_name)
);


