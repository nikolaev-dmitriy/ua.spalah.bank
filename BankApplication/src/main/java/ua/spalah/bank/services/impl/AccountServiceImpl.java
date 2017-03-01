package main.java.ua.spalah.bank.services.impl;

import main.java.ua.spalah.bank.commands.BankCommander;
import main.java.ua.spalah.bank.dao.AccountDao;
import main.java.ua.spalah.bank.dao.ClientDao;
import main.java.ua.spalah.bank.exceptions.NotEnoughFundsException;
import main.java.ua.spalah.bank.exceptions.OverdraftLimitExceededException;
import main.java.ua.spalah.bank.models.Client;
import main.java.ua.spalah.bank.models.accounts.Account;
import main.java.ua.spalah.bank.models.accounts.CheckingAccount;
import main.java.ua.spalah.bank.services.AccountService;

/**
 * Created by Man on 07.01.2017.
 */
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private ClientDao clientDao;

    public AccountServiceImpl(ClientDao clientDao, AccountDao accountDao) {
        this.accountDao = accountDao;
        this.clientDao = clientDao;
    }

    @Override
    public void deposit(Account account, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative");
        account.setBalance(account.getBalance() + amount);
        accountDao.update(BankCommander.currentClient.getId(),account);
    }

    @Override
    public void withdraw(Account account, double amount) throws NotEnoughFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative");

        switch (account.getType()) {
            case SAVING: {
                double balance = account.getBalance();
                if (balance >= amount) {
                    account.setBalance(balance - amount);
                    accountDao.update(BankCommander.currentClient.getId(),account);
                } else {
                    throw new NotEnoughFundsException(balance);
                }
                break;
            }
            case CHECKING: {
                double available = account.getBalance() + ((CheckingAccount) account).getOverdraft();
                if (available >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    accountDao.update(BankCommander.currentClient.getId(),account);
                } else {
                    throw new OverdraftLimitExceededException(available);
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown account type");
        }
    }

    @Override
    public void transfer(Account fromAccount, Account toAccount, double amount) throws NotEnoughFundsException {

    }

    @Override
    public Account setActiveAccount(Account account) {
        return accountDao.setActiveAccount(BankCommander.currentClient.getId(),account.getId());
    }

    @Override
    public void addAccount(Client client, Account account) {
        account = accountDao.saveOrUpdate(client.getId(),account);
        client.setActiveAccount(account);
        clientDao.update(client);
    }

}
