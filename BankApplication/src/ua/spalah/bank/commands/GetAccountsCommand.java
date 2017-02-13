package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class GetAccountsCommand extends AbstractCommand implements Command {

    private ClientService clientService;

    public GetAccountsCommand(ClientService clientService) {
        super(new ConsoleIO());
        this.clientService = clientService;
    }

    public GetAccountsCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;
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
