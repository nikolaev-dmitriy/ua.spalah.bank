package main.java.ua.spalah.bank.commands;

import main.java.ua.spalah.bank.IO.ConsoleIO;
import main.java.ua.spalah.bank.IO.IO;
import main.java.ua.spalah.bank.services.AccountService;
import main.java.ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 13.01.2017.
 */
public class SelectActiveAccountCommand extends AbstractCommand implements Command {
    private ClientService clientService;
    private AccountService accountService;

    public SelectActiveAccountCommand(ClientService clientService, AccountService accountService) {
        super(new ConsoleIO());
        this.clientService = clientService;
        this.accountService = accountService;
    }

    public SelectActiveAccountCommand(ClientService clientService, AccountService accountService, IO io) {
        super(io);
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        GetAccountsCommand getAccountsCommand = new GetAccountsCommand(clientService, accountService, getIo());
        getAccountsCommand.execute();
        write("Enter the number of account to set it active\n");
        int i = Integer.parseInt(read().trim());
        try {
            BankCommander.currentClient.setActiveAccount(accountService.setActiveAccount(BankCommander.currentClient.getAccounts().get(i - 1)));

        } catch (IndexOutOfBoundsException e) {
            write("Account with this number is not existed\n");
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
