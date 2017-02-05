package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.services.BankReportService;

/**
 * Created by Man on 12.01.2017.
 */
public class GetBankInfoCommand extends AbstractCommand implements Command {
    private final BankReportService bankReportService;

    public GetBankInfoCommand(BankReportService bankReportService) {
        super(new ConsoleIO());
        this.bankReportService = bankReportService;
    }

    public GetBankInfoCommand(BankReportService bankReportService, IO io) {
        super(io);
        this.bankReportService = bankReportService;
    }

    @Override
    public void execute() {
        write("Cities:\n");
        for (String city : bankReportService.getClientsByCity(BankCommander.currentBank).keySet()) {
            write(city + bankReportService.getClientsByCity(BankCommander.currentBank).get(city) + "\n");
        }
        write("Number of bank's clients: " + bankReportService.getNumberOfClients(BankCommander.currentBank) + "\n");
        write("Number of bank's accounts: " + bankReportService.getNumberOfAccounts(BankCommander.currentBank) + "\n");
        write("Total bank's savings: " + bankReportService.getTotalAccountSum(BankCommander.currentBank) + "\n");
        write("Total bank's credits: " + bankReportService.getBankCreditSum(BankCommander.currentBank) + "\n");
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
