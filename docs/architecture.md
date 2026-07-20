# Arquitetura e critérios de aceite

## Camadas

```text
interfaces/rest  -> controllers e contratos HTTP
application      -> orquestra casos de uso e transações
domain           -> regras financeiras e Strategy Pattern
infrastructure   -> JPA, PostgreSQL, Flyway e configurações
```

O frontend segue a mesma intenção: `components/` contém componentes de apresentação reutilizáveis e `core/` centraliza os modelos e o acesso HTTP.

## Decisões técnicas

- `BigDecimal` representa dinheiro e taxas; nunca `double`.
- Cada tipo de recebível possui uma estratégia de spread própria.
- A taxa de câmbio é buscada após a precificação na moeda do ativo.
- A liquidação é persistida em transação única e registra taxa, spread, vencimento e prazo aplicados para auditoria.
- Flyway versiona o esquema PostgreSQL.
- Docker Compose orquestra PostgreSQL, API e painel Angular.

## Critérios de aceite

| Critério | Evidência |
|---|---|
| Cálculo correto | Testes unitários para os spreads das duas strategies |
| Precisão decimal | `BigDecimal` e arredondamento `HALF_UP` em quatro casas |
| Entrada segura | Bean Validation e tratamento global de exceções |
| Integridade | `@Transactional` no caso de uso de liquidação |
| Usabilidade | Formulário de câmbio, simulação, liquidação e extrato |
| Execução | `docker compose up --build` sobe os três serviços |

## Convenção de vencimento

O usuário informa a data de vencimento. O motor calcula internamente os períodos da fórmula a partir da quantidade de dias até o vencimento, dividida por 30 e arredondada para cima. Vencimento no dia atual equivale a período zero.
