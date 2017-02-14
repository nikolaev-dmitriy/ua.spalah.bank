package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.BankReportService;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class GetBankInfoCommand extends AbstractCommand implements Command {
    private final BankReportService bankReportService;
    private final ClientService clientService;

    public GetBankInfoCommand(BankReportService bankReportService, ClientService clientService) {
        super(new ConsoleIO());
        this.bankReportService = bankReportService;
        this.clientService = clientService;
    }

    public GetBankInfoCommand(BankReportService bankReportService, ClientService clientService, IO io) {
        super(io);
        this.bankReportService = bankReportService;
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        write("Bank Info:\n");
        for (Client client : bankReportService.getClientsSortedByName()) {
            write("Client: " + client.getName() + "\t" + client.getGender().name() + "\t" + client.getCity() + "\t" + client.getTelephone() + "\t" + client.getEmail() + "\n");
            try {
                write("Active account: " + clientService.findClientActiveAccount(client) + "\n");
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }
            write("Accounts: ");
            for (Account account : clientService.getClientAccounts(client)) {
                write(account + "\n");
            }
            write("Client's total balance = " + clientService.getTotalBalance(client) + "\n");
        }
        write("Count of clients = " + bankReportService.getNumberOfClients() + "\n");
        write("Count of accounts = " + bankReportService.getNumberOfAccounts() + "\n");
        write("Bank's total balance = " + bankReportService.getTotalAccountSum() + "\n");
        write("Bank's total credit = " + bankReportService.getBankCreditSum() + "\n");
    }

    @Override
    public String getCommandInfo() {
        return "Print info about bank";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return false;
    }
}
