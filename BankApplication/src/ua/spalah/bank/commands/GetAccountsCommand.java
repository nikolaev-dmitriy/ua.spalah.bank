package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.models.accounts.Account;

/**
 * Created by Man on 12.01.2017.
 */
public class GetAccountsCommand extends AbstractCommand implements Command {


    public GetAccountsCommand() {
        super(new ConsoleIO());
    }

    public GetAccountsCommand(IO io) {
        super(io);
    }

    @Override
    public void execute() {
        String accounts = BankCommander.currentClient.getName() + "'s accounts:\n";
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Account account : BankCommander.currentClient.getAccounts()) {
            if (account.equals(BankCommander.currentClient.getActiveAccount())) {
                stringBuilder.append("V");
                stringBuilder.append(" " + (++i) + "." + account + "\n");
            } else {
                stringBuilder.append("  " + (++i) + "." + account + "\n");
            }
        }

        write(accounts + stringBuilder.toString());
    }

    @Override
    public String getCommandInfo() {
        return "Print current client's list of accounts";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
