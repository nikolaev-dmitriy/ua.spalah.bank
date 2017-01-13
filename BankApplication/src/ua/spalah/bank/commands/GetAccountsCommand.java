package ua.spalah.bank.commands;

import ua.spalah.bank.models.accounts.Account;

/**
 * Created by Man on 12.01.2017.
 */
public class GetAccountsCommand implements Command {

    @Override
    public void execute() {
        String s=BankCommander.currentClient.getName()+"'s accounts:\n";
        int i=0;
        for (Account account : BankCommander.currentClient.getAccounts()) {
            if (account.equals(BankCommander.currentClient.getActiveAccount())) {
                i++;
                s+="V "+i+". "+account.toString()+"\n";
            } else {
                i++;
                s+="  "+i+". "+account.toString()+"\n";
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Print current client's list of accounts";
    }
}
