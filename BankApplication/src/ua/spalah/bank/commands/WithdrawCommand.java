package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.AccountService;

import java.util.Scanner;

/**
 * Created by Man on 12.01.2017.
 */
public class WithdrawCommand implements Command  {
    private final AccountService accountService;

    public WithdrawCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        Account activeAccount=BankCommander.currentClient.getActiveAccount();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter amount to withdraw:");
        double amount =in.nextDouble();
        try{
        accountService.withdraw(activeAccount, amount);
        } catch (NotEnoughFundsException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Withdraw amount from active account";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
