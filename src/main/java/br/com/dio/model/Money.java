package br.com.dio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa a unidade mínima de valor no sistema. 
 * Cada objeto Money carrega consigo o seu histórico de transações, 
 * permitindo rastrear por onde esse "centavo" passou.
 */
public class Money {

    // Lista de auditorias que descrevem o caminho desta unidade de dinheiro
    private final List<MoneyAudit> history = new ArrayList<>();

    public Money(final MoneyAudit history){
        this.history.add(history);
    }

    public  void addHistory(final MoneyAudit history){
        this.history.add(history);
    }

    public List<MoneyAudit> getHistory() {
        return Collections.unmodifiableList(history);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(history, money.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(history);
    }

    @Override
    public String toString() {
        return "Money{" + "history=" + history + '}';
    }
}
