package ua.spalah.bank.commands;


import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Man on 12.01.2017.
 */
public class DepositCommand extends AbstractCommand implements Command {
    private final AccountService accountService;

    public DepositCommand(AccountService accountService) {
        super(new ConsoleIO());
        this.accountService = accountService;
    }

    public DepositCommand(AccountService accountService, IO io) {
        super(io);
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        write("Enter the amount to deposit\n");
        double amount = Double.parseDouble(read().trim());
        try {
            accountService.deposit(BankCommander.currentClient.getActiveAccount(), amount);
        } catch (IllegalArgumentException e) {
            write(e.getMessage() + "\n");
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
