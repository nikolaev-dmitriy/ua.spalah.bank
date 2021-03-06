package ua.spalah.bank.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.exceptions.OverdraftLimitExceededException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Man on 07.01.2017.
 */
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ClientDao clientDao;

    @Transactional
    @Override
    public void deposit(long clientId, Account account, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative");
        account.setBalance(account.getBalance() + amount);
        accountDao.update(clientId, account);
    }
    @Transactional
    @Override
    public void withdraw(long clientId, Account account, double amount) throws NotEnoughFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative");

        switch (account.getAccountType()) {
            case SAVING: {
                double balance = account.getBalance();
                if (balance >= amount) {
                    account.setBalance(balance - amount);
                    accountDao.update(clientId, account);
                } else {
                    throw new NotEnoughFundsException(balance);
                }
                break;
            }
            case CHECKING: {
                double available = account.getBalance() + ((CheckingAccount) account).getOverdraft();
                if (available >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    accountDao.update(clientId, account);
                } else {
                    throw new OverdraftLimitExceededException(available);
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown account type");
        }
    }
    @Transactional
    @Override
    public void transfer(Account fromAccount, Account toAccount, double amount) throws NotEnoughFundsException {

    }
    @Transactional
    @Override
    public Account setActiveAccount(Client client, Account account) {
        account = accountDao.setActiveAccount(client.getId(), account.getId());
        client.setActiveAccount(account);
        return client.getActiveAccount();
    }

    @Override
    public Account findAccountById(long id) {
        return accountDao.find(id);
    }
    @Transactional
    @Override
    public Account updateAccount(long clientId, Account account) {
        return accountDao.update(clientId, account);
    }
    @Transactional
    @Override
    public Account addAccount(Client client, Account account) {
        account = accountDao.save(client.getId(), account);
        account = setActiveAccount(client, account);
        return account;
    }

}
