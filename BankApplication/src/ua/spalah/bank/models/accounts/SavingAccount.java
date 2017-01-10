package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Man on 07.01.2017.
 */
public class SavingAccount implements Account {
    private double balance;
    private final AccountType accountType;

    protected SavingAccount(double balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

    public SavingAccount(double balance) {
        this(balance, AccountType.SAVING);
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
        return "\nSavingAccount{" +
                "balance=" + this.getBalance() +
                ", accountType=" + this.getType() +
                "}";
    }

}

