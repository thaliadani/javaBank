# DIO Bank - Sistema de Gestão Bancária e Investimentos

Este é um sistema bancário desenvolvido em Java, focado na gestão de contas via chaves Pix e operações de investimento. O projeto utiliza uma arquitetura baseada em **Repositórios** e **Modelos de Domínio** para garantir a integridade das transações financeiras.

## 🚀 Funcionalidades

### Gestão de Contas (`AccountWallet`)
- **Criação de Contas:** Permite registrar contas associadas a uma ou mais chaves Pix.
- **Operações Financeiras:** Depósitos, saques e transferências entre contas.
- **Histórico de Transações:** Registro detalhado (`MoneyAudit`) de todas as movimentações, permitindo a visualização do extrato agrupado por data e hora.

### Gestão de Investimentos (`InvestmentWallet`)
- **Portfólio de Investimentos:** Criação de produtos de investimento com taxas de rendimento específicas.
- **Carteiras de Investimento:** Vinculação de uma conta corrente a um produto de investimento.
- **Atualização Automática:** Funcionalidade para aplicar rendimentos (taxas) em todas as carteiras ativas.
- **Resgate e Aporte:** Movimentação de fundos entre a conta principal e a carteira de investimentos.

## 🛠️ Tecnologias Utilizadas

- **Java 17+**: Uso intensivo de Streams, Optional e novas APIs de data (`OffsetDateTime`).
- **Gradle**: Gerenciador de dependências e automação de build.
- **Arquitetura**: Separação clara entre modelos, repositórios e exceções customizadas.

## 📂 Estrutura do Projeto

- `br.com.dio.model`: Contém as classes de domínio (`Money`, `AccountWallet`, `InvestmentWallet`, `MoneyAudit`).
- `br.com.dio.repository`: Camada de persistência em memória e lógica de negócio para manipulação dos dados.
- `br.com.dio.exception`: Exceções personalizadas para tratamento de erros de negócio (ex: `NoFundsEnoughException`, `PixInUseException`).
- `br.com.dio.Main`: Interface de linha de comando (CLI) para interação com o usuário.

## 📋 Como Executar

1. Certifique-se de ter o **JDK 17** ou superior instalado.
2. Clone o repositório.
3. Execute o projeto via terminal utilizando o Gradle:
   ```bash
   ./gradlew run
   ```
4. Siga as instruções no console para navegar pelo menu de operações.

## 🛡️ Regras de Negócio Implementadas

- Não é permitido criar contas com chaves Pix que já estejam em uso.
- Operações de saque ou transferência validam automaticamente se há saldo suficiente.
- O rendimento de investimentos é calculado com base no saldo atual da carteira de investimentos.
- O histórico de transações é imutável, garantindo a rastreabilidade do "dinheiro" dentro do sistema.

---
*Projeto desenvolvido para fins de estudo sobre lógica de programação orientada a objetos e estruturas de dados em Java.*