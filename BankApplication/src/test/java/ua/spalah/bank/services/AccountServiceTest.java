/*
package ua.spalah.bank.services;

import org.junit.Before;
import org.junit.Test;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.exceptions.OverdraftLimitExceededException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.impl.AccountServiceImpl;

import static org.junit.Assert.assertEquals;

*/
/**
 * Created by Man on 23.01.2017.
 *//*

public class AccountServiceTest {

    private AccountService accountService;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountServiceImpl();
        account1 = new CheckingAccount(1000, 2000);
        account2 = new SavingAccount(4000);
    }


    @Test
    public void deposit() throws Exception {
        double amount = 2500;
        double expexted = account1.getBalance() + amount;
        accountService.deposit(account1, amount);
        assertEquals(expexted, account1.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositWithException() {
        double amount = -100;
        accountService.deposit(account1, amount);
    }

    @Test
    public void testWithdraw() throws Exception {
        double amount = 500;
        double expected = account1.getBalance() - amount;
        accountService.withdraw(account1, amount);
        assertEquals(expected, account1.getBalance(), 0);
    }

    @Test(expected = NotEnoughFundsException.class)
    public void testWithdrawFailsWhenNotEnoughMoney() throws NotEnoughFundsException {
        double amount = 4001;
        accountService.withdraw(account2, amount);
    }

    @Test(expected = OverdraftLimitExceededException.class)
    public void withdrawException2() throws NotEnoughFundsException {
        double amount = 3001;
        accountService.withdraw(account1, amount);
    }

    @Test
    public void transfer() throws Exception {
        double amount = 3000;
        double expected1 = account1.getBalance() - amount;
        double expected2 = account2.getBalance() + amount;
        accountService.transfer(account1, account2, amount);
        assertEquals(expected1, account1.getBalance(), 0);
        assertEquals(expected2, account2.getBalance(), 0);
    }

}*/
