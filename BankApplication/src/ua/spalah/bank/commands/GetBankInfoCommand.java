package ua.spalah.bank.commands;

import ua.spalah.bank.services.BankReportService;

/**
 * Created by Man on 12.01.2017.
 */
public class GetBankInfoCommand implements Command {
    private final BankReportService bankReportService;

    public GetBankInfoCommand(BankReportService bankReportService) {
        this.bankReportService = bankReportService;
    }

    @Override
    public void execute() {
        System.out.println("Clients: "+bankReportService.getClientsSortedByName(BankCommander.currentBank));
        System.out.println("Number of clients: "+bankReportService.getNumberOfClients(BankCommander.currentBank));
        System.out.println("Number of accounts: "+bankReportService.getNumberOfAccounts(BankCommander.currentBank));
        System.out.println("Total bank's savings: "+bankReportService.getTotalAccountSum(BankCommander.currentBank));
        System.out.println("Total bank's credits: "+bankReportService.getBankCreditSum(BankCommander.currentBank));
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
