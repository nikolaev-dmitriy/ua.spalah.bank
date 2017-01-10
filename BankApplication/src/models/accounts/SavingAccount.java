package models.accounts;

/**
 * Created by Man on 07.01.2017.
 */
public class SavingAccount extends AbstractAccount {

    public SavingAccount(double balance) {
        super(balance);
        setAccountType(AccountType.SAVING);
    }
    @Override
    public String toString() {
        return "\nSavingAccount{" +
                "balance=" + this.getBalance()+
                ", accountType=" + this.getAccountType()  +
                "}";
    }
}

