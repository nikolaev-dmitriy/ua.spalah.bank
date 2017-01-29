package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Man on 12.01.2017.
 */
public class WithdrawCommand implements Command  {
    private final AccountService accountService;
    private final IO io;

    public WithdrawCommand(AccountService accountService) {
        io = new ConsoleIO();
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        io.write("Enter amount to withdraw:");
        double amount =Double.parseDouble(io.read().trim());
        try{
        accountService.withdraw(BankCommander.currentClient.getActiveAccount(), amount);
        } catch (NotEnoughFundsException | IllegalArgumentException e) {
            io.write(e.getMessage());
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
