package br.com.dio.model;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Representa um registro de auditoria para cada movimentação financeira.
 * Esta classe é imutável para garantir a integridade do histórico.
 */
public final class MoneyAudit {
        private final UUID transactionId;   // Identificador único da transação
        private final BankService targetService; // Serviço responsável (Conta Corrente ou Investimento)
        private final String description;    // Descrição amigável da operação
        private final long amount;           // Valor em centavos
        private final OffsetDateTime createdAt; // Data e hora da operação

        public MoneyAudit(UUID transactionId, BankService targetService, String description, long amount,
                        OffsetDateTime createdAt) {
                this.transactionId = transactionId;
                this.targetService = targetService;
                this.description = description;
                this.amount = amount;
                this.createdAt = createdAt;
        }

        public UUID transactionId() {
                return transactionId;
        }

        public BankService targetService() {
                return targetService;
        }

        public String description() {
                return description;
        }

        public long amount() {
                return amount;
        }

        public OffsetDateTime createdAt() {
                return createdAt;
        }
}
