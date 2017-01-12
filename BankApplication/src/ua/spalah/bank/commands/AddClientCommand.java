package ua.spalah.bank.commands;

import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class AddClientCommand implements Command {
    private final ClientService clientService;

    public AddClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
