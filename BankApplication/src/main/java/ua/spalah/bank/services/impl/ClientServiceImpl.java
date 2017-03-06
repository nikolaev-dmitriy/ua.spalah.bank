package ua.spalah.bank.services.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.ClientService;

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
    public Client saveClient(Client client) {
        return clientDao.save(client);
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
    public Account findClientActiveAccount(Client client){
        return accountDao.findActiveAccountByClientId(client.getId());
    }

    @Override
    public Client findClientById(long id){
        Client client = clientDao.find(id);
        client.getAccounts().addAll(accountDao.findByClientId(client.getId()));
        client.setActiveAccount(accountDao.findActiveAccountByClientName(client.getName()));
        return client;
    }

    @Override
    public Client updateClient(Client client) {
        client = clientDao.update(client);
        client.setActiveAccount(accountDao.findActiveAccountByClientName(client.getName()));
        return client;
    }

    @Override
    public void deleteClientById(long id) {
        clientDao.delete(id);
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
