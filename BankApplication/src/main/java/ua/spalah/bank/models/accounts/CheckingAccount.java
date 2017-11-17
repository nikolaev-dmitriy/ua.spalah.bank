package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Man on 07.01.2017.
 */
@Entity
@DiscriminatorValue("CHECKING")
public class CheckingAccount extends Account {
    @Column(name="overdraft")
    private double overdraft;

    public CheckingAccount() {
        super(AccountType.CHECKING);
    }

    public CheckingAccount(double balance, double overdraft) {
        super(balance, AccountType.CHECKING);
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
                ", accountType=" + this.getAccountType()+
                "}";
    }
    public boolean equals(Account account) {
        if (this.getOverdraft() == ((CheckingAccount) account).getOverdraft()  && this.getAccountType().equals(account.getAccountType()) && this.getId() == account.getId() && this.getBalance() == account.getBalance()) {
            return true;
        } else {
            return false;

        }
    }
}
