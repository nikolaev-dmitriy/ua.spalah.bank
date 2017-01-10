package models.accounts;

/**
 * Created by Man on 10.01.2017.
 */
public abstract class AbstractAccount implements Account {
    private double balance;
    AccountType accountType;

    public AbstractAccount(double balance) {
        this.balance = balance;
    }
@Override
    public AccountType getAccountType() {
        return accountType;
    }
@Override
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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
        return "";
    }
}
