package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Man on 07.01.2017.
 */
public class CheckingAccount extends SavingAccount {
    private double overdraft;


    public CheckingAccount(double balance, double overdraft) {
        super(balance, AccountType.CHECKING);
        this.overdraft = overdraft;
    }

    public CheckingAccount(long clientId, double balance, double overdraft) {
        super(clientId, balance, AccountType.CHECKING);
        this.overdraft = overdraft;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "balance=" + this.getBalance() +
                ", overdraft=" + this.getOverdraft() +
                ", accountType=" + this.getType() +
                "}";
    }

    @Override
    public boolean equals(Account account) {
        if (this.getOverdraft() == ((CheckingAccount) account).getOverdraft() && this.getClientId() == account.getClientId() && this.getType().equals(account.getType()) && this.getId() == account.getId() && this.getBalance() == account.getBalance()) {
            return true;
        } else {
            return false;

        }
    }
}
