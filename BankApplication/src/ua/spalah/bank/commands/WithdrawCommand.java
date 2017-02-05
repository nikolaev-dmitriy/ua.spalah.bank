package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Man on 12.01.2017.
 */
public class WithdrawCommand extends AbstractCommand implements Command {
    private final AccountService accountService;

    public WithdrawCommand(AccountService accountService) {
        super(new ConsoleIO());
        this.accountService = accountService;
    }

    public WithdrawCommand(AccountService accountService, IO io) {
        super(io);
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        write("Enter amount to withdraw:\n");
        double amount = Double.parseDouble(read().trim());
        try {
            accountService.withdraw(BankCommander.currentClient.getActiveAccount(), amount);
        } catch (NotEnoughFundsException | IllegalArgumentException e) {
            write(e.getMessage() + "\n");
        }
    }

    @Override
    public String getCommandInfo() {
        return "Withdraw amount from active account";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
