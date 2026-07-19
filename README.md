# SRM Credit Engine

Plataforma para simular e liquidar cessões de crédito em BRL e USD.

## Stack

- Java 21 e Spring Boot 3
- PostgreSQL e Flyway
- Angular e TypeScript
- Docker Compose

## Executar

```bash
docker compose up --build
```

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Painel: `http://localhost:4200`

## Fluxo do operador

1. Cadastre a taxa de câmbio quando as moedas forem diferentes.
2. Preencha cedente, valor, tipo, vencimento, taxa base e moedas.
3. A simulação ocorre automaticamente com os dados válidos.
4. Liquide a operação e consulte o extrato paginado.

O vencimento bloqueia datas passadas. O backend calcula o prazo em blocos de 30 dias, arredondados para cima, e registra a data e o prazo aplicado para auditoria.

## Regras de precificação

`valor presente = valor de face / (1 + taxa base + spread)^prazo`

- Duplicata mercantil: spread de 1,5% a.m. (`0.015`)
- Cheque pré-datado: spread de 2,5% a.m. (`0.025`)

Taxas usam formato decimal: `0.01` equivale a 1% ao mês. A conversão cambial é aplicada após a precificação.

## Endpoints

| Método | Rota | Finalidade |
|---|---|---|
| POST | `/api/v1/exchange-rates` | Cadastra taxa de câmbio |
| POST | `/api/v1/pricing/simulations` | Simula valor presente |
| POST | `/api/v1/settlements` | Registra liquidação atômica |
| GET | `/api/v1/settlements` | Extrato com filtros e paginação |

## Arquitetura e documentação

- [Arquitetura e critérios de aceite](docs/architecture.md)
- [Diagrama ER](docs/er-diagram.md)
- [Uso de IA](AI_USAGE.md)

O backend usa controllers, camada de aplicação, domínio com Strategy Pattern e infraestrutura de persistência. O frontend organiza interface em `components/` e contratos/HTTP em `core/`.

## Testes

O backend cobre strategies e a regra de vencimento. O frontend cobre o cliente HTTP de simulação e câmbio.

```bash
cd frontend
npm test -- --watch=false
```
