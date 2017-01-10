package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;


class FindClientCommand implements Command {

    private final ClientService clientService;

    public FindClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter client name");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        try {
            BankCommander.currentClient = clientService.findClientByName(BankCommander.currentBank, name);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Find client";
    }
}
