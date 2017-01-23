package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Man on 12.01.2017.
 */
public class TransferCommand implements Command {
    private final AccountService accountService;
    private final ClientService clientService;
    public TransferCommand(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        Scanner in=new Scanner(System.in);
        System.out.println("Enter the name of client whom you want transfer amount");
        String name=in.nextLine();
        try {
            System.out.println("Enter amount to transfer");
            double amount=in.nextDouble();
            accountService.transfer(BankCommander.currentClient.getActiveAccount(), clientService.findClientByName(BankCommander.currentBank, name).getActiveAccount(), amount);
        } catch (ClientNotFoundException | NotEnoughFundsException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Transfer from active account to another client's active account";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
