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
public class AddAccountCommand extends AbstractCommand implements Command {
    private final ClientService clientService;

    public AddAccountCommand(ClientService clientService) {
        super(new ConsoleIO());
        this.clientService = clientService;
    }

    public AddAccountCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        //Scanner in = new Scanner(System.in);
        Account account = null;
        double balance = -1;
        double overdraft = -1;
        boolean exit = false;
        while (exit == false) {
            write("Enter the type of account:\n1.Saving account\n2.Checking account\n");
            int i = Integer.parseInt(read().trim());
            switch (i) {
                case 1: {
                    write("Enter the start balance for your account\n");
                    balance = Double.parseDouble(read().trim());
                    if (balance < 0) {
                        write("Balance can't be negative\n");
                        break;
                    } else {
                        account = new SavingAccount(balance);
                        clientService.addAccount(BankCommander.currentClient, account);
                        selectThisAccountActive(account);
                        exit = true;
                        break;
                    }
                }
                case 2: {
                    write("Enter the start balance for your account\n");
                    balance = Double.parseDouble(read().trim());
                    write("Enter the overdraft for your account\n");
                    overdraft = Double.parseDouble(read().trim());
                    if (balance < 0 || overdraft < 0) {
                        write("Balance or overdraft can't be negative\n");
                        break;
                    } else {
                        account = new CheckingAccount(balance, overdraft);
                        clientService.addAccount(BankCommander.currentClient, account);
                        selectThisAccountActive(account);
                        exit = true;
                        break;
                    }
                }
                default: {
                    write("Incorrect input\n");
                    break;
                }
            }
        }
    }

    private void selectThisAccountActive(Account account) {
        if (BankCommander.currentClient.getAccounts().size() == 1) {
            BankCommander.currentClient.setActiveAccount(account);
        } else {
            boolean exitFromMethod = false;
            while (exitFromMethod == false) {
                write("Do you want to make this account active?\n1. Yes\n2.No\n");
                int answer = Integer.parseInt(read().trim());
                switch (answer) {
                    case 1: {
                        BankCommander.currentClient.setActiveAccount(account);
                        exitFromMethod = true;
                        break;
                    }
                    default: {
                        if (answer != 2) {
                            write("Incorrect input\n");
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
