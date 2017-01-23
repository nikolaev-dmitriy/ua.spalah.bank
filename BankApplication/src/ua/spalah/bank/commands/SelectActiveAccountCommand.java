package ua.spalah.bank.commands;

import java.util.Scanner;

/**
 * Created by Man on 13.01.2017.
 */
public class SelectActiveAccountCommand implements Command {
    @Override
    public void execute() {
        GetAccountsCommand getAccountsCommand = new GetAccountsCommand();
        getAccountsCommand.execute();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of account to set it active");
        int i = in.nextInt();
        try {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(i - 1));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Account with this number is not existed");
        }
    }
    @Override
    public String getCommandInfo() {
        return "Select active account for current client";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
