package ua.spalah.bank.services;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

/**
 * Created by Man on 07.01.2017.
 */
public interface AccountService {

    void deposit(long clientId, Account account, double amount);

    void withdraw(long clientId, Account account, double amount)throws NotEnoughFundsException;
    void transfer(Account fromAccount, Account toAccount, double amount)throws NotEnoughFundsException;

    Account updateAccount(long clientId, Account account);

    Account addAccount(Client client, Account account);

    Account setActiveAccount(Client client, Account account);

    Account findAccountById(long id);
}
