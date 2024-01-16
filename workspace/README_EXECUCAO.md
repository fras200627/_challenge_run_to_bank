# Challenge - Run the Bank

Este arquivo: 
**README_EXECUCAO.md**<br>
```
O ambiente baixado do Git precisa de configuração do 
SDK Java a ser utilizado.
O Mínimo é JDK 17.
O ambiente baixado do Git precisa de configuração do
Maven local a ser utilizado.

Todos os projetos possuem o arquivo:

    application-local.yml
    na pasta ..\resources

A execução de ser com parametro de VM:

    -Dspring.profiles.active=local
    
A pasta .idea existente no repositório Git
já traz todos os processos de RUN e Debug.

Sequência de subida dos serviços:

Service Discovery
Autehntication Server
API-Gateway
APIs de Branchs
APIs de Customers
APIs de Accounts
APIs de Transactions

IMPORTANTE:
AS APIs SÓ FUNCIONAM COM TOKENS VÁLIDOS NA AUTENTICAÇÃO.
PARA GERAR UM TOKEN USE O SERVIÇO DE Get Token JWT DO
AUTHENTICATION SERVER.
NO POSTMAN JÁ EXISTE UM REQUEST DESSE SERVIÇO.

** OS TOKENS SÃO GERADOS COM TEMPO LIMITE DE 30 MINUTOS.

```

## Estrutura do Projeto Challenge Run the Bank
-  **01.cloud**<br>
project-service-discovery<br>
project-api-gateway<br>
-  **02.authentication.server**<br>
project-authentication-server
-  **03.branchs**<br>
project-branchs-domain-api<br>
project-branchs-business-api
-  **04.customers**<br>
project-customers-domain-api<br>
project-customers-business-api
- **05.accounts**<br>
project-accounts-domain-api<br>
project-accounts-business-api
-  **06.transactions**<br>
project-transactions-domain-api<br>
project-transactions-business-api