# Uso de IA

Este projeto usou IA como copiloto de engenharia. O autor revisou as decisões, executou o sistema e mantém responsabilidade integral pelo código entregue.

## Uso estratégico

- Planejamento de escopo para entrega de nível pleno.
- Estrutura Spring Boot, Angular e Docker Compose.
- Modelagem de strategies, migrations, documentação e testes unitários.
- Separação do frontend em componentes reutilizáveis e camada `core`.
- Melhorias de UX: simulação automática, validação de vencimento, filtros e paginação.

## Revisões e correções

- O requisito pede vencimento, não apenas prazo. O contrato foi alterado para receber `dueDate`; o backend calcula e audita o prazo aplicado.
- A tela dependia do Swagger para câmbio. Foi criado formulário de taxa de câmbio no painel.
- Uma chamada do extrato deixou de enviar filtros obrigatórios. O erro Angular foi identificado no build Docker e corrigido.
- O OneDrive bloqueou dependências locais do Angular. O frontend passou a ser compilado em container Docker, sem depender de `node_modules` local para demonstração.

## Análise crítica

A IA acelerou estruturas repetitivas, documentação e propostas de teste. Ela não substituiu a validação prática de contratos, cálculos financeiros, migrations e builds. Esses pontos foram revisados durante o desenvolvimento.
