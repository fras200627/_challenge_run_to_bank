-- Create sequence
create sequence CHALLENGE.SEQ_ACCOUNTS
minvalue 222000
maxvalue 999999
start with 222000
increment by 1
cache 20;

create table CHALLENGE.TB_ACCOUNTS(
    account_id      bigint       not null,
    branch_id       bigint       not null,
    customer_id     bigint       not null,
    account_number  varchar(11)  not null,
    account_status  varchar(20)  not null,
    account_balance NUMBER       not null DEFAULT 0,
    create_At       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_At       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_code       varchar2(20),
    primary         key(account_id),
    unique          (account_number)
);


