-- Create sequence
create sequence CHALLENGE.SEQ_CUSTOMERS
minvalue 111000
maxvalue 999999
start with 111005
increment by 1
cache 20;

create table CHALLENGE.TB_CUSTOMERS(
    customer_id       bigint       not null,
    customer_type     char(1)      not null,
    customer_document varchar(18)  not null,
    customer_status   varchar(20)  not null,
    customer_name     varchar(100) not null,
    customer_address  varchar(300) not null,
    customer_password varchar(200) not null,
    create_At         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_At         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_code         varchar2(20),
    primary           key(customer_id),
    UNIQUE            (customer_document)
);


