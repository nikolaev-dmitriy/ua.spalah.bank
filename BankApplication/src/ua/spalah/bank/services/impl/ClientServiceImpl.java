package ua.spalah.bank.services.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.dao.impl.AccountDaoImpl;
import ua.spalah.bank.dao.impl.ClientDaoImpl;
import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
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

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client findClientByName(String name) throws ClientNotFoundException {
        Client client = clientDao.findByName(name);
        if (client == null) {
            throw new ClientNotFoundException(name);
        } else {
            return client;
        }
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
        AccountDao accountDao = new AccountDaoImpl(clientDao.getConnection());
        return accountDao.findByClientId(client.getId());
    }

    @Override
    public Account findClientActiveAccount(Client client) throws ClientNotFoundException {
        AccountDao accountDao = new AccountDaoImpl(clientDao.getConnection());
        return accountDao.findActiveAccountByClientName(client.getName());
    }

    @Override
    public Account setActiveAccount(Account account) {
        AccountDao accountDao = new AccountDaoImpl(clientDao.getConnection());
        return accountDao.setActiveAccount(account.getId());
    }

    @Override
    public double getTotalBalance(Client client) {
        double totalSum = 0;
        AccountDao accountDao = new AccountDaoImpl(clientDao.getConnection());
        for (Account account : accountDao.findByClientId(client.getId())) {
            totalSum += account.getBalance();
        }
        return totalSum;
    }

    @Override
    public void addAccount(Client client, Account account) {
        AccountDao accountDao = new AccountDaoImpl(clientDao.getConnection());
        account = accountDao.saveOrUpdate(account);
        client.setActiveAccountId(account.getId());
        client.setActiveAccount(account);
        ClientDao clientDao = new ClientDaoImpl(accountDao.getConnection());
        clientDao.update(client);
    }
}
