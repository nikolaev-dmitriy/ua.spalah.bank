package ua.spalah.bank.tests;

import org.junit.*;
import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Man on 22.01.2017.
 */
public class ClientServiceTest extends Assert {
    private static ClientService clientService;
    private static Bank bank;

    @BeforeClass
    public static void initBeforeAllTests() {
        clientService = new ClientServiceImpl();
        bank = new Bank();
    }

    @Before
    public void init() {
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

    @After
    public void clear() {
        bank.getClients().clear();
    }

    @AfterClass
    public static void clearAfterAll() {
        clientService = null;
        bank.getClients().clear();
        bank = null;
    }

    @Test
    public void findClientByName() throws Exception {
        Client testMasha = clientService.findClientByName(bank, "Masha");
        Client masha = new Client("Masha", Gender.FEMALE, "sf4325gfd@yandex.ru", "+380936345673", "Dnipro");
        assertEquals(masha, testMasha);
    }

    @Test
    public void saveClient() throws Exception {
        Client jim = new Client("Jim", Gender.MALE, "jim.moriarty@sherlock.com", "+380665431827", "London");
        Bank tempBank = new Bank();
        Account svac = new SavingAccount(1000);
        Account ckac = new CheckingAccount(2000, 4000);
        tempBank.getClients().put("Dima", new Client("Dima", Gender.MALE, "florida124@yandex.ru", "+380936678673", "Dnipro"));
        tempBank.getClients().put("Masha", new Client("Masha", Gender.FEMALE, "sf4325gfd@yandex.ru", "+380936345673", "Dnipro"));
        tempBank.getClients().put("Kostya", new Client("Kostya", Gender.MALE, "fgd5454565546562@yandex.ru", "+380945675673", "Kryvyi Rig"));
        tempBank.getClients().put("Misha", new Client("Misha", Gender.MALE, "mikokonst@yandex.ru", "+380666678673", "Dnipro"));
        for (Client client : tempBank.getClients().values()) {
            client.getAccounts().add(svac);
            client.getAccounts().add(svac);
            client.getAccounts().add(svac);
            client.getAccounts().add(ckac);
        }
        tempBank.getClients().put("Jim", jim);
        clientService.saveClient(bank, jim);
        assertEquals(tempBank.getClients(), bank.getClients());
    }

    @Test
    public void findAllClients() throws Exception {
        Map<String, Client> clients = new HashMap<String, Client>();
        clients.put("Dima", new Client("Dima", Gender.MALE, "florida124@yandex.ru", "+380936678673", "Dnipro"));
        clients.put("Masha", new Client("Masha", Gender.FEMALE, "sf4325gfd@yandex.ru", "+380936345673", "Dnipro"));
        clients.put("Kostya", new Client("Kostya", Gender.MALE, "fgd5454565546562@yandex.ru", "+380945675673", "Kryvyi Rig"));
        clients.put("Misha", new Client("Misha", Gender.MALE, "mikokonst@yandex.ru", "+380666678673", "Dnipro"));
        assertEquals(clients, clientService.findAllClients(bank));
    }

    @Test
    public void deleteClient() throws Exception {
        Map<String, Client> clients = new HashMap<String, Client>();
        clients.put("Dima", new Client("Dima", Gender.MALE, "florida124@yandex.ru", "+380936678673", "Dnipro"));
        clients.put("Masha", new Client("Masha", Gender.FEMALE, "sf4325gfd@yandex.ru", "+380936345673", "Dnipro"));
        clients.put("Kostya", new Client("Kostya", Gender.MALE, "fgd5454565546562@yandex.ru", "+380945675673", "Kryvyi Rig"));
        clients.put("Misha", new Client("Misha", Gender.MALE, "mikokonst@yandex.ru", "+380666678673", "Dnipro"));
        clients.remove("Misha");
        clientService.deleteClient(bank, "Misha");
        assertEquals(clients, bank.getClients());
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
        clientService.findClientByName(bank,"Max");
    }
}