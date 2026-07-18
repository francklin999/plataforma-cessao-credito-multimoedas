# SRM Credit Engine

Plataforma para simular e liquidar cessões de crédito em BRL e USD.

## Stack

- Java 21 e Spring Boot 3
- PostgreSQL
- Angular e TypeScript
- Docker Compose

## Estrutura

- `backend/`: API REST, regras de negócio e persistência.
- `frontend/`: painel do operador.
- `docs/`: diagramas e decisões técnicas.

Documentação complementar: [arquitetura e aceite](docs/architecture.md) e [diagrama ER](docs/er-diagram.md).

## Executar

Com Docker Desktop em execução:

```bash
docker compose up --build
```

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Painel do operador: `http://localhost:4200`

## Regras de precificação

As taxas devem ser enviadas em formato decimal: `0.01` equivale a 1% ao mês.

`valor presente = valor de face / (1 + taxa base + spread)^prazo em meses`

- Duplicata mercantil: spread de 1,5% a.m. (`0.015`)
- Cheque pré-datado: spread de 2,5% a.m. (`0.025`)

Quando as moedas forem diferentes, a conversão é aplicada sobre o valor presente. A taxa pode ser cadastrada no painel ou pelo endpoint. O vencimento é informado pelo operador e o prazo em meses é calculado pelo backend para manter o cálculo auditável.

## Endpoints iniciais

| Método | Rota | Finalidade |
|---|---|---|
| POST | `/api/v1/exchange-rates` | Cadastra uma taxa de câmbio |
| POST | `/api/v1/pricing/simulations` | Simula o valor presente |
| POST | `/api/v1/settlements` | Registra uma liquidação atômica |
| GET | `/api/v1/settlements` | Extrato paginado com filtros |

Exemplo de taxa BRL/USD:

```json
{"sourceCurrency":"BRL","targetCurrency":"USD","rate":0.20}
```

Exemplo de simulação:

```json
{
  "faceValue": 1000.00,
  "receivableType": "DUPLICATA_MERCANTIL",
  "dueDate": "2026-08-18",
  "baseRate": 0.01,
  "assetCurrency": "BRL",
  "paymentCurrency": "USD"
}
```
