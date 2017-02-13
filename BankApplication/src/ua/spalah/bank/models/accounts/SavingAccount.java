package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Man on 07.01.2017.
 */
public class SavingAccount implements Account {
    private long id;
    private double balance;
    private final AccountType accountType;
    private long clientId;

    protected SavingAccount(double balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

    protected SavingAccount(long clientId, double balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
        this.clientId = clientId;
    }

    public SavingAccount(double balance) {
        this(balance, AccountType.SAVING);
    }

    public SavingAccount(long clientId, double balance) {
        this(clientId, balance, AccountType.SAVING);
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
    public long getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(long clientId) {
        this.clientId = clientId;
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
                ", accountType=" + this.getType() +
                "}";
    }

    @Override
    public boolean equals(Account account) {
        if (this.getClientId() == account.getClientId() && this.getType().equals(account.getType()) && this.getId() == account.getId() && this.getBalance() == account.getBalance()) {
            return true;
        } else {
            return false;

        }
    }
}

