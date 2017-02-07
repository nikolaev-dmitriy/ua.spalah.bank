package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Man on 07.01.2017.
 */
public class SavingAccount implements Account {
    private long id=0;
    private double balance;
    private final AccountType accountType;

    protected SavingAccount(double balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
        this.id=this.id+1;
    }

    public SavingAccount(double balance) {
        this(balance, AccountType.SAVING);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {

        return id;
    }

    @Override
    public AccountType getType() {
        return accountType;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "SavingAccount{" +
                "balance=" + this.getBalance() +
                ", accountType=" + this.getType() +
                "}";
    }

}

