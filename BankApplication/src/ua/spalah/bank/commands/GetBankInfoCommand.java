package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.services.BankReportService;

/**
 * Created by Man on 12.01.2017.
 */
public class GetBankInfoCommand implements Command {
    private final BankReportService bankReportService;
    private final IO io;

    public GetBankInfoCommand(BankReportService bankReportService) {
        io = new ConsoleIO();
        this.bankReportService = bankReportService;
    }

    @Override
    public void execute() {
        io.write("Cities:");
        for (String city : bankReportService.getClientsByCity(BankCommander.currentBank).keySet()) {
            io.write(city+bankReportService.getClientsByCity(BankCommander.currentBank).get(city));
        }
        io.write("Number of bank's clients: "+bankReportService.getNumberOfClients(BankCommander.currentBank));
        io.write("Number of bank's accounts: "+bankReportService.getNumberOfAccounts(BankCommander.currentBank));
        io.write("Total bank's savings: "+bankReportService.getTotalAccountSum(BankCommander.currentBank));
        io.write("Total bank's credits: "+bankReportService.getBankCreditSum(BankCommander.currentBank));
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
