package br.com.dio.model;

import java.util.List;

import static br.com.dio.model.BankService.ACCOUNT;

public class AccountWallet extends Wallet{

    private final List<String> pix;


    public AccountWallet(final List<String> pix){
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount,"valor de criação da conta");
    }

    public  void  addMoney(final long amount, final String description){
        if (amount <= 0) return;
        List<Money> newMoney = generateMoney(amount, description);
        this.money.addAll(newMoney);
    }

    public List<String> getPix() {
        return pix;
    }
}
