package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Man on 12.01.2017.
 */
public class RemoveClientCommand implements Command {
    private final ClientService clientService;

    public RemoveClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client, who will be removed");
        String name = in.nextLine();
        try {
            Client client =clientService.findClientByName(BankCommander.currentBank, name);
            if (BankCommander.currentClient == client) {BankCommander.currentClient = null;}
            clientService.deleteClient(BankCommander.currentBank, client);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public String getCommandInfo() {
        return "Remove client";
    }
}
