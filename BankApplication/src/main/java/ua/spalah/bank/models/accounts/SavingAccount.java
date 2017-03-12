package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Man on 07.01.2017.
 */

@Entity
@DiscriminatorValue("SAVING")
public class SavingAccount extends Account {
    public SavingAccount() {
        super (AccountType.SAVING);
    }

    protected SavingAccount(double balance, AccountType accountType) {
        super(balance, accountType);
    }

    public SavingAccount(double balance) {
        this(balance, AccountType.SAVING);
    }
    @Override
    public String toString() {
        return "SavingAccount{" +
                "balance=" + this.getBalance() +
                ", accountType=" + this.getAccountType() +
                "}";
    }
    public boolean equals(Account account) {
        if ( this.getAccountType().equals(account.getAccountType()) && this.getId() == account.getId() && this.getBalance() == account.getBalance()) {
            return true;
        } else {
            return false;

        }
    }
}

