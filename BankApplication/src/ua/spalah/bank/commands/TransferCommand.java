package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class TransferCommand implements Command {
    private final AccountService accountService;
    private final ClientService clientService;
    private final IO io;

    public TransferCommand(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
        io = new ConsoleIO();
    }

    public TransferCommand(AccountService accountService, ClientService clientService, IO io) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.io = io;
    }

    @Override
    public void execute() {
        io.write("Enter the name of client whom you want transfer amount\n");
        String name = io.read().trim();
        try {
            io.write("Enter amount to transfer\n");
            double amount = Double.parseDouble(io.read().trim());
            if (!BankCommander.currentClient.equals(clientService.findClientByName(BankCommander.currentBank, name))) {
                accountService.transfer(BankCommander.currentClient.getActiveAccount(), clientService.findClientByName(BankCommander.currentBank, name).getActiveAccount(), amount);
            } else {
                io.write("Transfer error: You cant make the transfer to your active account from your active account\n");
            }
        } catch (ClientNotFoundException | NotEnoughFundsException | IllegalArgumentException e) {
            io.write(e.getMessage()+"\n");
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
