# Diagrama ER e catálogos de domínio

Nesta versão, **Moeda** e **Produto/Tipo de Recebível** são catálogos lógicos implementados por enums no código. Isso reduz complexidade no MVP sem perder a integridade dos valores aceitos. As tabelas persistidas são Taxas de Câmbio e Liquidações.

```mermaid
erDiagram
    CURRENCY_CATALOG ||--o{ EXCHANGE_RATES : "origem"
    CURRENCY_CATALOG ||--o{ EXCHANGE_RATES : "destino"
    CURRENCY_CATALOG ||--o{ SETTLEMENTS : "moeda do ativo"
    CURRENCY_CATALOG ||--o{ SETTLEMENTS : "moeda de pagamento"
    RECEIVABLE_PRODUCT ||--o{ SETTLEMENTS : "classifica"
    EXCHANGE_RATES }o--o{ SETTLEMENTS : "cotação aplicada"

    CURRENCY_CATALOG {
        string code PK "BRL | USD (enum)"
    }

    RECEIVABLE_PRODUCT {
        string code PK "DUPLICATA_MERCANTIL | CHEQUE_PRE_DATADO (enum)"
        decimal monthly_spread "regra de strategy"
    }

    EXCHANGE_RATES {
        uuid id PK
        string source_currency FK
        string target_currency FK
        decimal rate
        datetime effective_at
        bigint version
    }

    SETTLEMENTS {
        uuid id PK
        string cedent
        string receivable_type FK
        decimal face_value
        date due_date
        string asset_currency FK
        string payment_currency FK
        decimal base_rate
        decimal applied_spread
        decimal exchange_rate "snapshot da cotação"
        decimal present_value
        datetime created_at
    }
```

## Relações e auditoria

- Uma moeda pode participar de várias taxas, tanto como origem quanto como destino.
- Um produto/tipo de recebível classifica várias liquidações e determina a strategy de spread.
- Uma liquidação registra um **snapshot** do spread e da cotação usados; ela não depende de uma taxa futura para reproduzir o valor liquidado.
- A aplicação consulta somente taxa com `effective_at <= instante da operação`, ordenada da mais recente para a mais antiga.

## DDL

O DDL versionado está em `backend/src/main/resources/db/migration/`:

- `V1__create_credit_engine_tables.sql`: cria `exchange_rates` e `settlements`.
- `V2__add_settlement_due_date.sql`: adiciona vencimento e remove o campo técnico de prazo da estrutura final.
