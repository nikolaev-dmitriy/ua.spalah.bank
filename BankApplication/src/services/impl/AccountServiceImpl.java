package services.impl;

import exceptions.NotEnoughFundsException;
import exceptions.OverdraftLimitExceededException;
import models.accounts.Account;
import models.accounts.CheckingAccount;
import services.AccountService;

import static models.accounts.AccountType.CHECKING;
import static models.accounts.AccountType.SAVING;

/**
 * Created by Man on 07.01.2017.
 */
public class AccountServiceImpl implements AccountService  {
    public void deposit(Account account, double amount){
        if(amount>0){
        account.setBalance(account.getBalance() + amount);
        }else{
            throw new IllegalArgumentException("Amount can't be negative");
        }
    }
    public void withdraw(Account account,double amount)throws NotEnoughFundsException{
        if(amount>account.getBalance()){
            if(account.getType().equals(CHECKING)){
                CheckingAccount checkingAccount =(CheckingAccount) account ;
                try{
                    double credit=checkingAccount.getBalance()-amount;
                    double overdraft=checkingAccount.getOverdraft() +credit;
                    checkingAccount.setOverdraft(overdraft);
                    checkingAccount.setBalance(0);
                }catch(IllegalArgumentException e){
                    throw new OverdraftLimitExceededException();
                }
            }else if(account.getType().equals(SAVING)){
                throw new NotEnoughFundsException();
            }
        }else{
            account.setBalance(account.getBalance() - amount);
        }
    }
    public void transfer(Account fromAccount,Account toAccount,double amount)throws NotEnoughFundsException{

            withdraw(fromAccount, amount);
            deposit(toAccount, amount) ;

    }
}
