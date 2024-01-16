# Challenge - Run the Bank

Este arquivo:
**README_MODELAGEM_DADOS.md**<br>
Apresentação e detalhes dos modelos de dados utilizado<br>

### Diretório do H2 Database
- 00.database.h2

### Os databases H2 são:
- db_branchs
- db_customers
- db_accounts
- db_transactions

Foi seguido a definição de isolamento dos databases e a integração é feita através de serviços de clients.

**db_branchs**<br>
Base para registros das BRANCHS (Agências) que suportam criação das ACCOUNTS (Contas)<br>
A sequence é usada para criar os Ids das branchs<br>
As branchs possuem Id a partir de 1500<br>
São criadas automaticamente 04 branchs quando o database for criado. 
```
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
```
**db_customers**<br>
Base para registros dos CUSTOMERS (Clientes) que suportam criação das ACCOUNTS (Contas)<br>
A sequence é usada para criar os Ids dos customers<br>
Os customers possuem Id a partir de 111000<br>
São criados automaticamente 03 customers PF e 01 customers PJ quando o database for criado.
```
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
```
**db_accounts**<br>
Base para registros das ACCOUNTS (Contas) que suportam criação as TRANSACTIONS de transferências e estornos<br>
A sequence é usada para criar os Ids das Accounts<br>
As accounts possuem Id a partir de 222000

**Sobre os accounts numbers:**<br>
Os account_Number são definidos como: <BRANCH_ID>-<NR DA CONTA><br>
Exemplo: 1500-222000<br>

**Sobre a integridade Referencial**<br>
Sempre são usados os Ids de cada tabela

Não são criadas accounts automaticamente quando o database for criado.
```
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
```
**db_transactions**<br>
Base para registros das das TRANSACTIONS de transferências e estornos<br>
A sequence é usada para criar os Ids das Transactions<br>
As transactions possuem Id a partir de 9999000<br>
Não são criadas transactions automaticamente quando o database for criado.
```
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
```