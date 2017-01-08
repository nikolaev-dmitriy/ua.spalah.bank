import exceptions.BankException;
import models.Bank;
import models.Client;
import models.accounts.CheckingAccount;
import models.accounts.SavingAccount;
import services.AccountService;
import services.BankReportService;
import services.ClientService;
import services.impl.AccountServiceImpl;
import services.impl.BankReportServiceImpl;
import services.impl.ClientServiceImpl;

/**
 * Created by Man on 07.01.2017.
 */
public class Main {
    public static void main(String[] args)throws BankException {
        Bank bank=new Bank();
        Client dima=new Client("Dima","Male");
        Client misha=new Client("Misha","Male");
        Client masha=new Client("Masha","Female");
        Client kostya=new Client("Kostya","Male");
        CheckingAccount dck=new CheckingAccount(1000,800);
        SavingAccount dsa=new SavingAccount(3000);
        CheckingAccount mick=new CheckingAccount(17000,7000);
        SavingAccount misa=new SavingAccount(5000);
        CheckingAccount mack=new CheckingAccount(4000,5000);
        SavingAccount masa=new SavingAccount(100000);
        CheckingAccount kck=new CheckingAccount(2000,1000);
        SavingAccount ksa=new SavingAccount(20000);
        ClientService clientService =new ClientServiceImpl();
        clientService.saveClient(bank,dima);
        clientService.saveClient(bank,misha);
        clientService.saveClient(bank,masha);
        clientService.saveClient(bank,kostya);
        clientService.addAccount(dima,dck);
        clientService.addAccount(dima,dsa);
        clientService.addAccount(misha,mick);
        clientService.addAccount(misha,misa);
        clientService.addAccount(masha,masa);
        clientService.addAccount(masha,mack);
        clientService.addAccount(kostya,ksa);
        clientService.addAccount(kostya,kck);
        System.out.println(clientService.findAllClients(bank).toString());
        clientService.deleteClient(bank,clientService.findClientByName(bank,"Kostya"));
        System.out.println(clientService.findAllClients(bank).toString());
        System.out.println(clientService.getTotalBalance(dima));
        AccountService accountService = new AccountServiceImpl();
        accountService.deposit(dima.getActiveAccount(),2500);
        accountService.withdraw(masha.getActiveAccount(),67000);
        accountService.transfer(misha.getActiveAccount(), dima.getActiveAccount(), 15000);
        accountService.transfer(dima.getActiveAccount(), masha.getActiveAccount(), 19000);
        System.out.println(clientService.findAllClients(bank).toString());
        BankReportService bankReportService = new BankReportServiceImpl();
        System.out.println(bankReportService.getNumberOfClients(bank));
        System.out.println(bankReportService.getNumberOfAccounts(bank));
        System.out.println(bankReportService.getTotalAccountSum(bank));
        System.out.println(bankReportService.getBankCreditSum(bank));
        System.out.println(bankReportService.getClientsSortedByName(bank));
    }
}
