package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Man on 13.01.2017.
 */
public class AddAccountCommand implements Command {
    private final ClientService clientService;

    public AddAccountCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client to add account");
        String name = in.nextLine();
        Client client = null;
        try {
            client = clientService.findClientByName(BankCommander.currentBank, name);
            BankCommander.currentClient = client;
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Enter the type of account:\n1.Saving account\n2.Checking account");
        int i = in.nextInt();
        switch (i) {
            case 1: {
                System.out.println("Enter the start balance for your account");
                double balance = in.nextDouble();
                SavingAccount savingAccount = new SavingAccount(balance);
                clientService.addAccount(client, savingAccount);
                break;
            }
            case 2: {
                System.out.println("Enter the start balance for your account");
                double balance = in.nextDouble();
                System.out.println("Enter the overdraft for your account");
                double overdraft = in.nextDouble();
                CheckingAccount checkingAccount = new CheckingAccount(balance, overdraft);
                clientService.addAccount(client, checkingAccount);
                break;
            }
            default: {
                System.out.println("Incorrect input");
                break;
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Add account for client";
    }
}
