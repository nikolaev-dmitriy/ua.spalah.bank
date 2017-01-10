package models.accounts;

/**
 * Created by Man on 07.01.2017.
 */
public interface Account {
    AccountType getAccountType();
    void setAccountType(AccountType accountType);
    double getBalance();

    void setBalance(double balance);
}
