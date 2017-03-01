package main.java.ua.spalah.bank.services.impl;

import main.java.ua.spalah.bank.dao.AccountDao;
import main.java.ua.spalah.bank.dao.ClientDao;
import main.java.ua.spalah.bank.models.Client;
import main.java.ua.spalah.bank.models.accounts.Account;
import main.java.ua.spalah.bank.models.accounts.CheckingAccount;
import main.java.ua.spalah.bank.models.type.AccountType;
import main.java.ua.spalah.bank.services.BankReportService;

import java.util.*;

/**
 * Created by Man on 07.01.2017.
 */
public class BankReportServiceImpl implements BankReportService {
    private ClientDao clientDao;
    private AccountDao accountDao;
    public BankReportServiceImpl(ClientDao clientDao, AccountDao accountDao) {
    this.clientDao=clientDao;
    this.accountDao = accountDao;
    }

    @Override
    public int getNumberOfClients() {
        return clientDao.findAll().size();
    }

    @Override
    public int getNumberOfAccounts() {
        return accountDao.findAll().size();
    }

    @Override
    public double getTotalAccountSum() {
        double totalSum = 0;
        for (Client client : clientDao.findAll()) {
            for (Account account : accountDao.findByClientId(client.getId())) {
                totalSum += account.getBalance();
            }
        }
        return totalSum;
    }

    @Override
    public double getBankCreditSum() {
        double totalCredit = 0;
        for (Client client : clientDao.findAll()) {
            for (Account account : accountDao.findByClientId(client.getId())) {
                if (account.getType().equals(AccountType.CHECKING)) {
                    CheckingAccount checkingAccount = (CheckingAccount) account;
                    totalCredit += checkingAccount.getOverdraft();
                }
            }
        }
        return totalCredit;
    }

    @Override
    public List<Client> getClientsSortedByName() {
        List<Client> clients = new ArrayList<Client>(clientDao.findAll());
        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                return client1.getName().compareTo(client2.getName());
            }
        });
        return clients;
        //return new TreeMap<>(bank.getClients());
    }

    @Override
    public Map<String, List<Client>> getClientsByCity() {
        Map<String, List<Client>> clientsByCity = new HashMap<>();
        for (Client client : clientDao.findAll()) {
            if (!clientsByCity.containsKey(client.getCity())) {
                clientsByCity.put(client.getCity(), new ArrayList<>());
            }
            clientsByCity.get(client.getCity()).add(client);
        }
        return clientsByCity;
    }
}
