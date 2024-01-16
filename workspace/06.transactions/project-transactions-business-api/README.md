# Challenge - Run the Bank

## APIs Transactions

### Apresentação:
Fornece os serviços de cadastro e listagem de dados.<br>
de transactions.

-  **06-transactions**<br>
   project-transactions-domain-api<br>
   project-transactions-business-api



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
