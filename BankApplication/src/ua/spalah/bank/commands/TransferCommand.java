package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class TransferCommand extends AbstractCommand implements Command {
    private final AccountService accountService;
    private final ClientService clientService;

    public TransferCommand(AccountService accountService, ClientService clientService) {
        super(new ConsoleIO());
        this.accountService = accountService;
        this.clientService = clientService;
    }

    public TransferCommand(AccountService accountService, ClientService clientService, IO io) {
        super(io);
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        write("Enter the name of client whom you want transfer amount\n");
        String name = read().trim();
        Account accountTo = null;
        try {
            accountTo = clientService.findClientByName(name).getActiveAccount();
        } catch (ClientNotFoundException e) {
            write(e.getMessage()+"\n");
            return;
        }
        try {
            write("Enter amount to transfer\n");
            double amount = Double.parseDouble(read().trim());
            if (!BankCommander.currentClient.equals(clientService.findClientByName( name))) {
                accountService.transfer(BankCommander.currentClient.getActiveAccount(),accountTo , amount);
            } else {
                write("Transfer error: You cant make the transfer to your active account from your active account\n");
            }
        } catch (ClientNotFoundException | NotEnoughFundsException | IllegalArgumentException e) {
            write(e.getMessage() + "\n");
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
