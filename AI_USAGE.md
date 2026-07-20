# Uso de IA como copiloto de engenharia

Este projeto foi desenvolvido com apoio intensivo do Codex como copiloto. A IA acelerou tarefas de estrutura, investigação e documentação; as decisões de escopo, revisões de código, testes e validações foram feitas pelo autor durante o desenvolvimento.

## Prompts estratégicos utilizados

Exemplos representativos das solicitações feitas durante o projeto:

- “Vamos focar no nível pleno e ver por onde eu começo.”
- “Deixe a arquitetura do front-end intuitiva como uma pasta de components e separe eles para que possa ser escalável.”
- “Faça esses testes unitários: prazo zero, vários meses, duplicata, cheque, strategy inexistente, conversão, moedas iguais, cotação ausente, cotação vigente versus futura, persistência e rollback.”
- “Atualize o README e o AI_USAGE com as atualizações e melhorias.”
- “Ainda acho `termInMonths` lixo; usamos vencimento. Vamos corrigir esses caras.”

Essas solicitações foram usadas para gerar opções de implementação, revisar riscos e acelerar alterações repetitivas. Não foram usadas como substituto para entendimento do código.

## Onde a IA economizou tempo

- Criação do esqueleto Spring Boot, Angular, Docker Compose e migrations Flyway.
- Proposta inicial das strategies de precificação e seus testes unitários.
- Organização da API em camadas e do Angular em `components/` e `core/`.
- Geração de documentação inicial: README, diagrama ER, critérios de aceite e exemplos de requisição.
- Diagnóstico de problemas de ambiente: Java no `PATH`, WSL/Docker e bloqueio de `node_modules` pelo OneDrive.

## Erros, interpretações incompletas e correções realizadas

| Situação | Problema identificado | Correção tomada |
|---|---|---|
| Prazo versus vencimento | A primeira proposta expunha e persistia `termInMonths`, embora o requisito peça vencimento. | O autor solicitou a remoção do campo público. `dueDate` tornou-se a fonte de verdade; os períodos da fórmula permanecem somente como detalhe interno do motor. |
| Vigência cambial | A consulta inicial poderia retornar a cotação mais recente mesmo se ela fosse futura. | A consulta foi alterada para `effectiveAt <= Instant.now()` com ordenação descendente; o cenário foi coberto por teste. |
| Frontend inicial | A primeira tela concentrava formulário, resultado e extrato no `AppComponent`. | A arquitetura foi reorganizada em componentes reutilizáveis, com modelos e serviço HTTP em `core/`. |
| Simulação e UX | A interface dependia de botão manual de simulação e permitia fluxo pouco guiado. | O formulário passou a iniciar vazio, bloquear vencimentos passados e disparar simulação automática apenas com dados válidos. |
| Câmbio | A primeira versão exigia usar Swagger para cadastrar taxa de câmbio. | Foi criado um formulário específico no painel do operador. |
| Build Angular | Houve sugestões que não consideraram corretamente que o método de extrato passou a exigir filtros. Isso gerou erro TypeScript no build. | O erro foi lido no log Docker, a chamada foi corrigida e o contrato de paginação foi alinhado. |
| Dependências locais | A instalação local de Angular falhou por bloqueios do OneDrive em `node_modules`. | O frontend passou a ser compilado em container Docker, removendo a dependência do ambiente local para a demonstração. |

## Decisões tomadas pelo autor

- Manter arquitetura em camadas em vez de simplificar para MVC puro, pois regras financeiras e persistência ficam melhor isoladas.
- Usar `BigDecimal` e `HALF_UP` para valores monetários, sem `double`.
- Registrar a taxa de câmbio e o spread usados na liquidação para auditoria.
- Usar vencimento como entrada de negócio e esconder o cálculo de períodos da API, banco e interface.
- Priorizar Docker Compose, testes, documentação e fluxo Git de pleno antes de diferenciais de nível sênior.
- Melhorar estilos e experiência do operador incrementalmente, validando a tela em execução antes de cada commit.

## Como o resultado foi validado

- Build e execução com Docker Compose para PostgreSQL, API e frontend.
- Migration Flyway aplicada no PostgreSQL.
- Fluxo manual validado: cadastro BRL/USD, simulação e liquidação registrada no extrato.
- Testes unitários para strategies, vencimento, câmbio vigente/ausente, persistência e falha antes da persistência.
- Testes Angular para o cliente HTTP de simulação e câmbio.
- Logs de build Docker utilizados para detectar e corrigir erros TypeScript.

## Análise crítica

A IA foi especialmente útil para reduzir trabalho repetitivo e manter uma visão do escopo. Ela dificultou quando sugestões iniciais assumiram contratos incompletos ou quando alterações encadeadas exigiram correções de compilação. O benefício veio do ciclo de revisão: sugerir, executar, ler o erro, ajustar e validar.

Todo código entregue foi revisado e ajustado pelo autor durante o desenvolvimento. A IA não substituiu responsabilidade técnica, conhecimento do domínio financeiro ou validação de segurança e integridade.
