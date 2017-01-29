package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.services.ClientService;


public class FindClientCommand implements Command {
    private final IO io;
    private final ClientService clientService;

    public FindClientCommand(ClientService clientService) {
        io = new ConsoleIO();
        this.clientService = clientService;
    }

    public FindClientCommand(IO io, ClientService clientService) {
        this.io = io;
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        io.write("Please enter client name");
        String name = io.read().trim();

        try {
            BankCommander.currentClient = clientService.findClientByName(BankCommander.currentBank, name);
        } catch (ClientNotFoundException e) {
            io.write(e.getMessage());
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
