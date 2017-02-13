package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class RemoveClientCommand extends AbstractCommand implements Command {
    private final ClientService clientService;


    public RemoveClientCommand(ClientService clientService) {
        super(new ConsoleIO());
        this.clientService = clientService;
    }

    public RemoveClientCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;

    }

    @Override
    public void execute() {
        write("Enter the name of client, who will be removed\n");
        String name = read().trim();

        try {
            if (BankCommander.currentClient != null) {
                if (BankCommander.currentClient.equals(clientService.findClientByName(name))) {
                    BankCommander.currentClient = null;
                }
            }
            clientService.deleteClient(name);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
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
