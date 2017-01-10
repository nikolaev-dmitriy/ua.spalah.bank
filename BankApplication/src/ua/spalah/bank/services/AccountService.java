package ua.spalah.bank.services;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.accounts.Account;

/**
 * Created by Man on 07.01.2017.
 */
public interface AccountService {
    void deposit(Account account, double amount);
    void withdraw(Account account,double amount)throws NotEnoughFundsException;
    void transfer(Account fromAccount, Account toAccount, double amount)throws NotEnoughFundsException;
}
