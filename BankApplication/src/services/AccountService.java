package services;

import models.accounts.Account;

/**
 * Created by Man on 07.01.2017.
 */
public interface AccountService {
    void deposit(Account account, double amount);
    void withdraw(Account account,double amount);
    void transfer(Account fromAccount, Account toAccount, double amount);
}
