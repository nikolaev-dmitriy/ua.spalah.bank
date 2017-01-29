package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Man on 12.01.2017.
 */
public class DepositCommand implements Command {
    private final AccountService accountService;
    private final IO io;

    public DepositCommand(AccountService accountService) {
        this.accountService = accountService;
        io = new ConsoleIO();
    }

    public DepositCommand(AccountService accountService, IO io) {
        this.accountService = accountService;
        this.io = io;
    }

    @Override
    public void execute() {
        io.write("Enter the amount to deposit");
        double amount = Double.parseDouble(io.read().trim());
        try {
            accountService.deposit(BankCommander.currentClient.getActiveAccount(), amount);
        } catch (IllegalArgumentException e) {
            io.write(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Deposit amount to active account";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
