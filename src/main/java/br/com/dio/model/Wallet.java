package br.com.dio.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class Wallet {

    private final BankService service;
    protected final List<Money> money;
    private final List<MoneyAudit> historyLog = new ArrayList<>();

    public Wallet(final BankService serviceType) {
        this.service = serviceType;
        this.money = new ArrayList<>();
    }

    public BankService getService() {
        return service;
    }

    protected List<Money> generateMoney(final long amount, final String description){
        MoneyAudit history = new MoneyAudit(UUID.randomUUID(), service, description, amount, OffsetDateTime.now());
        this.historyLog.add(history);
        return Stream.generate(() -> new Money(history)).limit(amount).toList();
    }

    public long getFunds(){
        return money.size();
    }

    public void addMoney(final List<Money> money, final BankService service, final String description){
        if (money.isEmpty()) return;
        MoneyAudit history = new MoneyAudit(UUID.randomUUID(), service, description, money.size(), OffsetDateTime.now());
        this.historyLog.add(history);
        money.forEach(m -> m.addHistory(history));
        this.money.addAll(money);
    }

    public List<Money> reduceMoney(final long amount, final String description) {
        int limit = Math.toIntExact(amount);
        MoneyAudit history = new MoneyAudit(UUID.randomUUID(), service, description, amount, OffsetDateTime.now());
        this.historyLog.add(history);
        List<Money> toRemove = new ArrayList<>(this.money.subList(0, limit));
        this.money.subList(0, limit).clear();
        return toRemove;
    }

    public List<MoneyAudit> getFinancialTransactions(){
        return new ArrayList<>(this.historyLog);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "service=" + service +
                ", money= R$" + String.format("%d,%02d", money.size() / 100, money.size() % 100) +
                '}';
    }
}