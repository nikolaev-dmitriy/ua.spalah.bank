/*
package ua.spalah.bank.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.impl.ClientServiceImpl;

*/
/**
 * Created by Man on 22.01.2017.
 *//*

public class ClientServiceTest extends Assert {
    private ClientService clientService;
    private Bank bank;

    @Before
    public void init() {
        clientService = new ClientServiceImpl();
        bank = new Bank();
        Account svac = new SavingAccount(1000);
        Account ckac = new CheckingAccount(2000, 4000);
        bank.getClients().put("Dima", new Client("Dima", Gender.MALE, "florida124@yandex.ru", "+380936678673", "Dnipro"));
        bank.getClients().put("Masha", new Client("Masha", Gender.FEMALE, "sf4325gfd@yandex.ru", "+380936345673", "Dnipro"));
        bank.getClients().put("Kostya", new Client("Kostya", Gender.MALE, "fgd5454565546562@yandex.ru", "+380945675673", "Kryvyi Rig"));
        bank.getClients().put("Misha", new Client("Misha", Gender.MALE, "mikokonst@yandex.ru", "+380666678673", "Dnipro"));
        for (Client client : bank.getClients().values()) {
            client.getAccounts().add(svac);
            client.getAccounts().add(svac);
            client.getAccounts().add(svac);
            client.getAccounts().add(ckac);
        }
    }

    @Test
    public void findClientByName() throws Exception {
        String name = "Masha";
        Client testMasha = clientService.findClientByName(bank, name);
        assertEquals(name, testMasha.getName());
    }

    @Test
    public void saveClient() throws Exception {
        Client jim = new Client("Jim", Gender.MALE, "jim.moriarty@sherlock.com", "+380665431827", "London");

        clientService.saveClient(bank, jim);

        Client foundClient = clientService.findClientByName(bank, "Jim");

        assertEquals(jim, foundClient);
    }

    @Test
    public void findAllClients() throws Exception {
        assertEquals(bank.getClients(), clientService.findAllClients(bank));
    }

    @Test
    public void deleteClient() throws Exception {
        Client misha = clientService.findClientByName(bank, "Misha");


        clientService.deleteClient(bank, "Misha");


        assertFalse(bank.getClients().containsValue(misha));
    }

    @Test
    public void getTotalBalance() throws Exception {
        Client dima = new Client("Dima", Gender.MALE, "florida124@yandex.ru", "+380936678673", "Dnipro");
        Account svac = new SavingAccount(1000);
        Account ckac = new CheckingAccount(2000, 4000);
        dima.getAccounts().add(svac);
        dima.getAccounts().add(ckac);
        assertEquals(3000, clientService.getTotalBalance(dima), 0);
    }

    @Test
    public void addAccount() throws Exception {
        Client dima = new Client("Dima", Gender.MALE, "florida124@yandex.ru", "+380936678673", "Dnipro");
        Account ck = new CheckingAccount(1000, 2000);
        dima.getAccounts().add(ck);
        Client client = clientService.findClientByName(bank, "Dima");
        clientService.addAccount(client, ck);
        assertEquals(dima.getAccounts().get(dima.getAccounts().size() - 1), client.getAccounts().get(client.getAccounts().size() - 1));
    }

    @Test(expected = ClientAlreadyExistsException.class)
    public void saveClientWithException() throws ClientAlreadyExistsException {
        Client dima = new Client("Dima", Gender.MALE, "florida124@yandex.ru", "+380936678673", "Dnipro");
        clientService.saveClient(bank, dima);
    }

    @Test(expected = ClientNotFoundException.class)
    public void getClientByNameWithException() throws ClientNotFoundException {
        clientService.findClientByName(bank, "Max");
    }
}*/
