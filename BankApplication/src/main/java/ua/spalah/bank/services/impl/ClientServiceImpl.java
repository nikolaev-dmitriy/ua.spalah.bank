package main.java.ua.spalah.bank.services.impl;

import main.java.ua.spalah.bank.dao.AccountDao;
import main.java.ua.spalah.bank.dao.ClientDao;
import main.java.ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import main.java.ua.spalah.bank.exceptions.ClientNotFoundException;
import main.java.ua.spalah.bank.models.Client;
import main.java.ua.spalah.bank.models.accounts.Account;
import main.java.ua.spalah.bank.services.ClientService;

import java.util.List;

/**
 * Created by Man on 07.01.2017.
 */
public class ClientServiceImpl implements ClientService {
    private ClientDao clientDao;
    private AccountDao accountDao;

    public ClientServiceImpl(ClientDao clientDao, AccountDao accountDao) {
        this.accountDao = accountDao;
        this.clientDao = clientDao;
    }

    @Override
    public Client findClientByName(String name) throws ClientNotFoundException {
        Client client = clientDao.findByName(name);
        if (client == null) {
            throw new ClientNotFoundException(name);
        }
        client.getAccounts().addAll(accountDao.findByClientId(client.getId()));
        client.setActiveAccount(accountDao.findActiveAccountByClientName(name));
        return client;
    }

    @Override
    public Client saveClient(Client client) throws ClientAlreadyExistsException {
        return clientDao.saveOrUpdate(client);
    }

    @Override
    public List<Client> findAllClients() {
        return clientDao.findAll();
    }


    @Override
    public void deleteClient(String name) throws ClientNotFoundException {
        Client client = findClientByName(name);
        clientDao.delete(client.getId());
    }

    @Override
    public List<Account> getClientAccounts(Client client) {
        return accountDao.findByClientId(client.getId());
    }

    @Override
    public Account findClientActiveAccount(Client client) throws ClientNotFoundException {
        return accountDao.findActiveAccountByClientName(client.getName());
    }


    @Override
    public double getTotalBalance(Client client) {
        double totalSum = 0;
        for (Account account : accountDao.findByClientId(client.getId())) {
            totalSum += account.getBalance();
        }
        return totalSum;
    }


}
