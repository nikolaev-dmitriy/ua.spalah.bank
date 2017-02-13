package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 13.01.2017.
 */
public class SelectActiveAccountCommand extends AbstractCommand implements Command {
    private ClientService clientService;

    public SelectActiveAccountCommand(ClientService clientService, IO io, ClientService clientService1) {
        super(new ConsoleIO());
        this.clientService = clientService;
    }

    public SelectActiveAccountCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        GetAccountsCommand getAccountsCommand = new GetAccountsCommand(clientService, getIo());
        getAccountsCommand.execute();
        write("Enter the number of account to set it active\n");
        int i = Integer.parseInt(read().trim());
        try {
            clientService.setActiveAccount(BankCommander.currentClient.getAccounts().get(i-1));
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
