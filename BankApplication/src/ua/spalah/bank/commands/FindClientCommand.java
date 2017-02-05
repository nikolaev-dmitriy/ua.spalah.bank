package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.services.ClientService;


public class FindClientCommand extends AbstractCommand implements Command {

    private final ClientService clientService;

    public FindClientCommand(ClientService clientService) {
        super(new ConsoleIO());
        this.clientService = clientService;
    }

    public FindClientCommand(IO io, ClientService clientService) {
        super(io);
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        write("Please enter client name\n");
        String name = read().trim();

        try {
            BankCommander.currentClient = clientService.findClientByName(BankCommander.currentBank, name);
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
