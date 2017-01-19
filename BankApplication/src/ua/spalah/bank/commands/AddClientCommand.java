package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

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
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client:");
        String name = in.nextLine();
        System.out.println("Enter the gender\n1.Male\n2.Female");
        int gender = in.nextInt();
        Client client = null;
        switch (gender) {
            case 1: {
                client = new Client(name, Gender.MALE);
                // clientService.saveClient(BankCommander.currentBank,client);
                break;
            }
            case 2: {
                client = new Client(name, Gender.FEMALE);
                break;
            }
            default: {
                IllegalArgumentException e = new IllegalArgumentException("Incorrect input!");
                System.out.println(e.getMessage());
                break;
            }
        }
        try {
            clientService.saveClient(BankCommander.currentBank, client);
            BankCommander.currentClient = client;
        } catch (ClientAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public String getCommandInfo() {
        return "Registration client";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return false;
    }


}
