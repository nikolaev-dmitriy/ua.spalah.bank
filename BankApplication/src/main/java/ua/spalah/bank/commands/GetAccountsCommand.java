package main.java.ua.spalah.bank.commands;

import main.java.ua.spalah.bank.IO.ConsoleIO;
import main.java.ua.spalah.bank.IO.IO;
import main.java.ua.spalah.bank.exceptions.ClientNotFoundException;
import main.java.ua.spalah.bank.models.accounts.Account;
import main.java.ua.spalah.bank.services.AccountService;
import main.java.ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class GetAccountsCommand extends AbstractCommand implements Command {

    private ClientService clientService;
    private AccountService accountService;
    public GetAccountsCommand(ClientService clientService, AccountService accountService) {
        super(new ConsoleIO());
        this.clientService = clientService;
        this.accountService = accountService;
    }

    public GetAccountsCommand(ClientService clientService, AccountService accountService, IO io) {
        super(io);
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        String accounts = BankCommander.currentClient.getName() + "'s accounts:\n";
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Account account : clientService.getClientAccounts(BankCommander.currentClient)) {
            try {
                if (account.getId() == clientService.findClientActiveAccount(BankCommander.currentClient).getId()) {
                    stringBuilder.append("V");
                    stringBuilder.append(" " + (++i) + "." + account + "\n");
                } else {
                    stringBuilder.append("  " + (++i) + "." + account + "\n");
                }
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }
        }

        write(accounts + stringBuilder.toString());
    }

    @Override
    public String getCommandInfo() {
        return "Print current client's list of accounts";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return true;
    }
}
