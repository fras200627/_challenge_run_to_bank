# Challenge - Run the Bank

## Apresentação:
Projeto com APIs para fornecer servicos de transações de PIX entre contas-correntes.

### Importante: Sobre os arquivos de documentação:
**README.md (este arquivo)**<br>
Apresentação do Challenge<br>
**README_PROJETOS.md**<br>
Apresentação e detalhes dos Serviços e APIs<br>
**README_MODELAGEM_DADOS.md**<br>
Apresentação e detalhes dos modelos de dados utilizado<br>
**README_PAYLOADS.md**<br>
Apresentação e detalhes dos payloads dos Serviços e APIs.
**README_EXECUCAO.md**<br>
Roteiro para subida dos serviços.

**Os servicos de cadastros são:**
- BRANCHS (Agências)
- CUSTOMERS (Clientes)
- ACCOUNTS (Contas-Correntes)

**Os Serviços de transações são:**
- TRANSACTIONS de transferências
- TRANSACTIONS de estorno de transferências

**O ambiente de execução possui:**

- Service Discovery para descoberta das APIs em execução 
- Service Discovery para escalabilidade de instances   
- Service Discovery para Load-Balance de uso das APIs
- API Gateway para direcionamento de acesso às APIs


- Authentication Server para geração de tokens e controle de acesso às APIs
- Authorization dos usuários Clients integrado ao Authentication
- Authorization usando regras de acesso por definição SCOPES para os Clients
- Utilização de OAuth2 com geração de tokens JWT e certificado JKS


- Comunicação entre as APIs com o uso dos recursos do Srping OpenFeign
- Bancos de dados exclusivos por APIs (branchs, customers, accounts e transactions)


- APIs usando o conceito DOMAIN SERVICES e BUSINESS SERVICES<br>
Todas as APIs de DOMAIN SERVICES possuem validação dos payloads recebidos<br>
Toda as APIs BUSINESS SERVICES possuem servicos de: FindById, FindAll, FindByPageable e 
FindByFilters (com reccursos de Pageable e filtros dinâmicos)  

## Tecnologias Utilizadas
- **Java JDK 17**
- **Spring Boot 2.7.15**
- **Spring Web**
- **Spring Cloud Eureka NetFlix (Server) (Service Discovery)**
- **Spring Cloud Eureka NetFlix (Client) (conexão dos clients)**
- **Spring Cloud Gateway (API Gateway)**
- **Spring Cloud OpenFeign**
- **Resilience4J**
- **Spring Security**
- **Spring OAuth2 Authentication Server**
- **Spring OAuth2 Resource Server**
- **Spring Data JPA**
- **Spring Validation**
- **Spring AOP**
- **Spring Actuator**
- **lombok**
- **H2 Database**
- **Flyway Database Migration**
- **Apache Commons**


### Observações sobre algumas Tecnologias utilizadas
**Service Discovery Eureka**<br>
É um dos projetos do cenário Spring para fornecer
solução de Service Discovery de APIs, controle de Instances e Load-Balance.

**Spring Cloud Gateway**<br>
Fornece a solução de API Gateway e é totalmente integrado ao Service Discovery Eureka.

**Spring Security OAUth2**<br>
Foi usado em todos os serviços a autenticação por Registered Clients com o uso de CLIENT_CREDENTIALS e autorização
por CLIENT SCOPES.

**Apache Camel**<br>
Foi usado o Spring OpenFeign como alternativa ao Apache Camel.<br>
O OpenFeign faz parte do framework Spring, fácil, menos verboso e simples de configurar.

**Resilience4**<br>
Solução externa ao Spring mas aceita, homolagada e sugerida pelo Springframework.org.<br>
Fornece a resiliência para as instances das APIs e possui funcionalidades de fallback para os
os serviços desejados.

**H2**<br>
Foi usado como solução de database com definição de criar os bancos em FILES no diretório 00.database.h2<br>
O H2 é executado no modo 'MYSQL' (..;MODE=MYSQL;AUTO_SERVER=TRUE) e está sendo gerenciado<br>
pelo migration da solução Flyway.

**Flyway**<br>
Foi usado como solução para gerenciar e automatizar os processos do H2.<br>
O Flyway é externo do Spring mas é aceito e homologado pelo Springframework.org. 

### Práticas adotadas

- Separação de funcionalidades entre DOMAIN SERVICES e BUSINESS SERVICES
- Consultas com filtros dinâmicos usando os recursos de JPA e JPA Specification
- Uso de RECORDS como alternativa aos DTOs clássicos
- Injeção de Dependências
- Tratamento de erros com responses padronizados
- Payloads de Responses 200 padronizados (com ticket number de controle)

### Regras de Negócio adotadas nas TRANSACTIONS
- **TRANSACTIONS de transferências**<br>
Verificação de existência das contas origem e destino e seus respectivos status.<br>
Contas Origem com status defirente de "ATIVA" não são permitidas para transferências<br>
Verificação de saldo disponível na Conta Origem<br>
Bloqueio da Conta-Origem no ínicio da TRANSACTION para não permitir transações concorrentes


- **TRANSACTIONS de ESTORNO de transferências**<br>
Verificação da existência do registro e status atual da TRANSACTION a ser cancelada<br>
Somente TRANSACTIONS com status "EFETIVADA" podem ser canceladas<br>
Verificação de saldo disponível na CONTA-DESTINO para execução do estorno<br>
Bloqueio da CONTA DESTINO no ínicio da TRANSACTION de cancelamento para
não permitir transações concorrentes e garantir o débito


## Estrutura do Projeto Challenge Run the Bank

### Diretórios do Projeto
```
..\_challenge_run_the_bank\workspace
```

 Sub-Diretórios em \workspace:
- 00.database.h2
- 01.cloud
- 02.authentication.server
- 03.branchs
- 04.customers
- 05.accounts
- 06.transactions
- 99.postman.scripts

### IDE IntelliJ como padrão
No repositório do GIT existe o diretório
```
..\_challenge_run_the_bank\idea
```

A intenção de disponibilizar o diretório .idea é fornecer todos os settings
do projeto Challenge: Settings do JDK, settings do Maven e funcinalidades de
execução das APIs.

### Diretório do H2 Database
- 00.database.h2

Os databases H2 db_branchs, db_customers, db_accounts e db_transactions ficam na pasta:
```
..\_challenge_run_the_bank\workspace\00.database.h2 
```

### Diretórios dos Service Discovery, API Gateway e Server Authentication
Os projetos dos serviços cloud e security estão nas pastas:
-  01.cloud
-  02.authentication.server

```
..\_challenge_run_the_bank\workspace\01.cloud
..\_challenge_run_the_bank\workspace\02.authentication.server

*********
O SERVICE DISCOVERY FICA NO LINK
    
    http://localhost:3000
    
A API GATEWAY FICA NO LINK

    http://localhost/3001
*******

``````

### Diretórios das APIs
Os projetos das APIs ficam nas pastas:
-  03.branchs
-  04.customers
-  05.accounts
-  06.transactions

```
..\_challenge_run_the_bank\workspace\03.branchs
..\_challenge_run_the_bank\workspace\04.customers
..\_challenge_run_the_bank\workspace\05.accounts
..\_challenge_run_the_bank\workspace\06.transactions
```

### Diretório dos Testes de Endpoints
Todos os serviços estão disponíveis para execução e testes usando o Postman.

"Project Challenge Run the Bank.postman_collection.json"

O arquivo do postman está disponível em:
- 99.postman.scripts

```
..\_challenge_run_the_bank\workspace\99.postman.scripts
```

