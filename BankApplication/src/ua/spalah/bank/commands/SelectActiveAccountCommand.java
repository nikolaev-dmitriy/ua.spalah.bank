package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;

/**
 * Created by Man on 13.01.2017.
 */
public class SelectActiveAccountCommand implements Command {
    private final IO io;

    public SelectActiveAccountCommand() {
        this.io = new ConsoleIO();
    }

    public SelectActiveAccountCommand(IO io) {
        this.io = io;
    }

    @Override
    public void execute() {
        GetAccountsCommand getAccountsCommand = new GetAccountsCommand(io);
        getAccountsCommand.execute();
        io.write("Enter the number of account to set it active\n");
        int i = Integer.parseInt(io.read().trim());
        try {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(i - 1));
        } catch (IndexOutOfBoundsException e) {
            io.write("Account with this number is not existed\n");
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
