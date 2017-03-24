//package ua.spalah.bank.commands;
//
//import ua.spalah.bank.IO.ConsoleIO;
//import ua.spalah.bank.IO.IO;
//import ua.spalah.bank.exceptions.NotEnoughFundsException;
//import ua.spalah.bank.services.AccountService;
//import ua.spalah.bank.services.ClientService;
//
///**
// * Created by Man on 12.01.2017.
// */
//public class WithdrawCommand extends AbstractCommand implements Command {
//    private final AccountService accountService;
//    private final ClientService clientService;
//    public WithdrawCommand(AccountService accountService,ClientService clientService) {
//        super(new ConsoleIO());
//        this.accountService = accountService;
//        this.clientService = clientService;
//    }
//
//    public WithdrawCommand(AccountService accountService,ClientService clientService, IO io) {
//        super(io);
//        this.accountService = accountService;
//        this.clientService = clientService;
//    }
//
//    @Override
//    public void execute() {
//        write("Enter amount to withdraw:\n");
//        double amount = Double.parseDouble(read().trim());
//        try {
//            accountService.withdraw(BankCommander.currentClient.getId(),BankCommander.currentClient.getActiveAccount(), amount);
//        } catch (NotEnoughFundsException | IllegalArgumentException e) {
//            write(e.getMessage() + "\n");
//        } catch (NullPointerException e) {
//            write("You have not any account\n");
//        }
//    }
//
//    @Override
//    public String getCommandInfo() {
//        return "Withdraw amount from active account";
//    }
//
//    @Override
//    public boolean currentClientIsNeeded() {
//        return true;
//    }
//}
