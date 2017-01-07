package services.impl;

import models.accounts.Account;
import services.AccountService;

/**
 * Created by Man on 07.01.2017.
 */
public class AccountServiceImpl implements AccountService  {
    public void deposit(Account account, double amount){
        account.setBalance(account.getBalance() +amount);
    }
    public void withdraw(Account account,double amount){
        account.setBalance(account.getBalance() - amount);
    }
    public void transfer(Account fromAccount,Account toAccount,double amount){
        if(fromAccount.getBalance()>=amount){
            toAccount.setBalance(amount);
            fromAccount.setBalance(fromAccount.getBalance()-amount);
        }
    }
}
