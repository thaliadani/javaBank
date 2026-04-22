package br.com.dio.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.INVESTMENT;

/**
 * Carteira de investimento vinculada a um produto de investimento específico 
 * e a uma conta corrente principal.
 */
public class InvestmentWallet extends Wallet{

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        // Ao iniciar, retira o valor inicial da conta corrente e adiciona nesta carteira
        addMoney(account.reduceMoney(amount, "Aplicação em " + investment.id()), getService(), "Investimento realizado");
    }

    public Investment getInvestment() {
        return investment;
    }

    public AccountWallet getAccount() {
        return account;
    }

    /**
     * Aplica o rendimento baseado em uma porcentagem sobre o saldo atual.
     */
    public void updateAmount(final long percent){
        long amount = getFunds() * percent / 100;
        if (amount <= 0) return;
        
        // Cria um novo registro de auditoria para o lucro gerado
        MoneyAudit history = new MoneyAudit(UUID.randomUUID(), getService(), "rendimentos", amount, OffsetDateTime.now());
        this.getFinancialTransactions().add(history); // Adiciona ao log da wallet
        List<Money> newMoney = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(newMoney);
    }

    @Override
    public String toString() {
        return super.toString() + "InvestmentWallet{" +
                "investment=" + investment +
                ", account=" + account +
                '}';
    }
}