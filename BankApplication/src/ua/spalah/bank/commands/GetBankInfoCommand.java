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

    public GetBankInfoCommand(BankReportService bankReportService, IO io) {
        this.bankReportService = bankReportService;
        this.io = io;
    }

    @Override
    public void execute() {
        io.write("Cities:\n");
        for (String city : bankReportService.getClientsByCity(BankCommander.currentBank).keySet()) {
            io.write(city + bankReportService.getClientsByCity(BankCommander.currentBank).get(city)+"\n");
        }
        io.write("Number of bank's clients: " + bankReportService.getNumberOfClients(BankCommander.currentBank)+"\n");
        io.write("Number of bank's accounts: " + bankReportService.getNumberOfAccounts(BankCommander.currentBank)+"\n");
        io.write("Total bank's savings: " + bankReportService.getTotalAccountSum(BankCommander.currentBank)+"\n");
        io.write("Total bank's credits: " + bankReportService.getBankCreditSum(BankCommander.currentBank)+"\n");
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
