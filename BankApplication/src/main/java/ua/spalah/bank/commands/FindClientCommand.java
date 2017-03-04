package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;


public class FindClientCommand extends AbstractCommand implements Command {

    private final ClientService clientService;
    private final AccountService accountService;

    public FindClientCommand(ClientService clientService, AccountService accountService) {
        super(new ConsoleIO());
        this.clientService = clientService;
        this.accountService = accountService;
    }


    public FindClientCommand(IO io, ClientService clientService, AccountService accountService) {
        super(io);
        this.clientService = clientService;
        this.accountService = accountService;

    }

    @Override
    public void execute() {
        write("Please enter client name\n");
        String name = read().trim();

        try {
            BankCommander.currentClient = clientService.findClientByName(name);
        } catch (ClientNotFoundException e) {
            write(e.getMessage() + "\n");
        }
    }

    @Override
    public String getCommandInfo() {
        return "Find client";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return false;
    }
}
