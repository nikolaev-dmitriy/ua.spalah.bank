package ua.spalah.bank.commands;

import ua.spalah.bank.models.accounts.Account;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Man on 13.01.2017.
 */
public class SelectActiveAccountCommand implements Command {
    @Override
    public void execute() {
        Scanner in=new Scanner(System.in);
        List<Account> accountList = BankCommander.currentClient.getAccounts();
        System.out.println("Enter the number of account to set it active");
        int i=in.nextInt();
        BankCommander.currentClient.setActiveAccount(accountList.get(i));
    }

    @Override
    public String getCommandInfo() {
        return "Select active account for current client";
    }
}
