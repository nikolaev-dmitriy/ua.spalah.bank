package ua.spalah.bank;

import ua.spalah.bank.exceptions.BankException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.ClientServiceImpl;

/**
 * Created by Man on 07.01.2017.
 */
public class Main {
    public static void main(String[] args) throws BankException {
        init();
    }

    private static Bank init() throws BankException {
        Bank bank = new Bank();
        Client dima = new Client("Dima", Gender.MALE);
        Client misha = new Client("Misha", Gender.MALE);
        Client masha = new Client("Masha", Gender.FEMALE);
        Client kostya = new Client("Kostya", Gender.MALE);
        CheckingAccount dck = new CheckingAccount(1000, 800);
        SavingAccount dsa = new SavingAccount(3000);
        CheckingAccount mick = new CheckingAccount(17000, 7000);
        SavingAccount misa = new SavingAccount(5000);
        CheckingAccount mack = new CheckingAccount(4000, 5000);
        SavingAccount masa = new SavingAccount(100000);
        CheckingAccount kck = new CheckingAccount(2000, 1000);
        SavingAccount ksa = new SavingAccount(20000);
        ClientService clientService = new ClientServiceImpl();
        clientService.saveClient(bank, dima);
        clientService.saveClient(bank, misha);
        clientService.saveClient(bank, masha);
        clientService.saveClient(bank, kostya);
        clientService.addAccount(dima, dck);
        clientService.addAccount(dima, dsa);
        clientService.addAccount(misha, mick);
        clientService.addAccount(misha, misa);
        clientService.addAccount(masha, masa);
        clientService.addAccount(masha, mack);
        clientService.addAccount(kostya, ksa);
        clientService.addAccount(kostya, kck);
        clientService.deleteClient(bank, clientService.findClientByName(bank, "Kostya"));
        AccountService accountService = new AccountServiceImpl();
        accountService.deposit(dima.getActiveAccount(), 2500);
        accountService.withdraw(masha.getActiveAccount(), 67000);
        accountService.transfer(misha.getActiveAccount(), dima.getActiveAccount(), 15000);
        accountService.transfer(dima.getActiveAccount(), masha.getActiveAccount(), 19000);
        return bank;
    }
}
