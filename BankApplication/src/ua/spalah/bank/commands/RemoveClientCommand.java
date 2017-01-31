package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class RemoveClientCommand implements Command {
    private final ClientService clientService;
    private final IO io;

    public RemoveClientCommand(ClientService clientService) {
        io = new ConsoleIO();
        this.clientService = clientService;
    }

    public RemoveClientCommand(ClientService clientService, IO io) {
        this.clientService = clientService;
        this.io = io;
    }

    @Override
    public void execute() {
        io.write("Enter the name of client, who will be removed\n");
        String name = io.read().trim();
        try {
            if (BankCommander.currentClient != null) {
                if (BankCommander.currentClient.equals(clientService.findClientByName(BankCommander.currentBank, name))) {
                    BankCommander.currentClient = null;
                }
            }
            if (BankCommander.currentBank.getClients().containsKey(name)) {
                clientService.deleteClient(BankCommander.currentBank, name);
            } else {
                throw new ClientNotFoundException(name);
            }
        } catch (ClientNotFoundException e) {
            io.write(e.getMessage()+"\n");
        }
    }

    @Override
    public String getCommandInfo() {
        return "Remove client";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return false;
    }
}
