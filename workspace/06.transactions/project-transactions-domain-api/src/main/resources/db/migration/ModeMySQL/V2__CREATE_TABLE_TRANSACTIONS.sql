-- Create sequence
create sequence CHALLENGE.SEQ_TRANSACTIONS
minvalue 9999000
maxvalue 9999999999
start with 9999000
increment by 1
cache 20;

create table CHALLENGE.TB_TRANSACTIONS(
    transaction_id           bigint       not null,
    transaction_type         varchar(20)  not null,
    transaction_status       varchar(20)  not null,
    transaction_description  varchar(50)  not null,
    account_id_origin        bigint       not null,
    account_id_destination   bigint       not null,
    transaction_value        NUMBER       not null DEFAULT 0,
    create_At                DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_code                varchar2(20) NOT NULL,
    primary key(transaction_id)
);


