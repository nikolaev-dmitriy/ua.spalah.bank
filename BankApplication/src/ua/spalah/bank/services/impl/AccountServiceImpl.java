package ua.spalah.bank.services.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.exceptions.OverdraftLimitExceededException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Man on 07.01.2017.
 */
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void deposit(Account account, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative");
        account = accountDao.find(account.getId());
        account.setBalance(account.getBalance() + amount);
        accountDao.update(account);
    }

    public void withdraw(Account account, double amount) throws NotEnoughFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative");

        switch (account.getType()) {
            case SAVING: {
                double balance = account.getBalance();
                if (balance >= amount) {
                    account.setBalance(balance - amount);
                    accountDao.update(account);
                } else {
                    throw new NotEnoughFundsException(balance);
                }
                break;
            }
            case CHECKING: {
                double available = account.getBalance() + ((CheckingAccount) account).getOverdraft();
                if (available >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    accountDao.update(account);
                } else {
                    throw new OverdraftLimitExceededException(available);
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown account type");
        }
    }

    public void transfer(Account fromAccount, Account toAccount, double amount) throws NotEnoughFundsException {
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
    }
}
