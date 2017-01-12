package ua.spalah.bank.commands;

/**
 * Created by Man on 12.01.2017.
 */
public class GetAccountsCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Active account: "+BankCommander.currentClient.getActiveAccount());
        System.out.println("Accounts: " +BankCommander.currentClient.getAccounts());
    }

    @Override
    public String getCommandInfo() {
        return "Print current client's list of accounts";
    }
}
