package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 13.01.2017.
 */
public class AddAccountCommand implements Command {
    private final ClientService clientService;
    private final IO io;

    public AddAccountCommand(ClientService clientService) {
        this.clientService = clientService;
        io = new ConsoleIO();
    }

    public AddAccountCommand(ClientService clientService, IO io) {
        this.clientService = clientService;
        this.io = io;
    }

    @Override
    public void execute() {
        //Scanner in = new Scanner(System.in);
        Account account = null;
        double balance = -1;
        double overdraft = -1;
        boolean exit = false;
        while (exit == false) {
            io.write("Enter the type of account:\n1.Saving account\n2.Checking account");
            int i = Integer.parseInt(io.read().trim());
            switch (i) {
                case 1: {
                    io.write("Enter the start balance for your account");
                    balance = Double.parseDouble(io.read().trim());
                    if (balance < 0) {
                        io.write("Balance can't be negative");
                        break;
                    } else {
                        account = new SavingAccount(balance);
                        clientService.addAccount(BankCommander.currentClient, account);
                        selectThisAccountActive(io, account);
                        exit = true;
                        break;
                    }
                }
                case 2: {
                    io.write("Enter the start balance for your account");
                    balance = Double.parseDouble(io.read().trim());
                    io.write("Enter the overdraft for your account");
                    overdraft = Double.parseDouble(io.read().trim());
                    if (balance < 0 || overdraft < 0) {
                        io.write("Balance or overdraft can't be negative");
                        break;
                    } else {
                        account = new CheckingAccount(balance, overdraft);
                        clientService.addAccount(BankCommander.currentClient, account);
                        selectThisAccountActive(io, account);
                        exit = true;
                        break;
                    }
                }
                default: {
                    io.write("Incorrect input");
                    break;
                }
            }
        }
    }

    private static void selectThisAccountActive(IO io, Account account) {
        if (BankCommander.currentClient.getAccounts().size() == 1) {
            BankCommander.currentClient.setActiveAccount(account);
        } else {
            boolean exitFromMethod = false;
            while (exitFromMethod == false) {
                io.write("Do you want to make this account active?\n1. Yes\n2.No");
                int answer = Integer.parseInt(io.read().trim());
                switch (answer) {
                    case 1: {
                        BankCommander.currentClient.setActiveAccount(account);
                        exitFromMethod = true;
                        break;
                    }
                    default: {
                        if (answer != 2) {
                            io.write("Incorrect input");
                        } else {
                            exitFromMethod = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Add account for current client";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }


}
