package br.com.dio.model;

public class Investment {
    private final long id;
    private final long tax;
    private final long initialFunds;

    public Investment(long id, long tax, long initialFunds) {
        this.id = id;
        this.tax = tax;
        this.initialFunds = initialFunds;
    }

    public long id() {
        return id;
    }

    public long tax() {
        return tax;
    }

    public long initialFunds() {
        return initialFunds;
    }

    @Override
    public String toString() {
        return "Investment{" +
                "id=" + id +
                ", tax=" + tax + "%" +
                ", initialFunds=R$" + String.format("%d,%02d", initialFunds / 100, initialFunds % 100) +
                '}';
    }
}
