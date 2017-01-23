package ua.spalah.bank.commands;

import ua.spalah.bank.services.AccountService;

import java.util.Scanner;

/**
 * Created by Man on 12.01.2017.
 */
public class DepositCommand implements Command {
    private final AccountService accountService;

    public DepositCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the amount to deposit");
            double amount = in.nextDouble();
            try {
                accountService.deposit(BankCommander.currentClient.getActiveAccount(), amount);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
    }
    @Override
    public String getCommandInfo() {
        return "Deposit amount to active account";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
