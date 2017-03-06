package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Man on 07.01.2017.
 */
public class SavingAccount implements Account {
    private long id;
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
    public AccountType getAccountType() {
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
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SavingAccount{" +
                "balance=" + this.getBalance() +
                ", accountType=" + this.getAccountType() +
                "}";
    }

    @Override
    public boolean equals(Account account) {
        if ( this.getAccountType().equals(account.getAccountType()) && this.getId() == account.getId() && this.getBalance() == account.getBalance()) {
            return true;
        } else {
            return false;

        }
    }
}

