package ua.spalah.bank.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
   @Autowired
    private ClientDao clientDao;
   @Autowired
   private AccountDao accountDao;

    @Override
    public Client findClientByName(String name) throws ClientNotFoundException {
        Client client = clientDao.findByName(name);
        if (client == null) {
            throw new ClientNotFoundException(name);
        }
        return client;
    }
    @Transactional
    @Override
    public Client saveClient(Client client) {
        return clientDao.save(client);
    }

    @Override
    public List<Client> findAllClients() {
        return clientDao.findAll();
    }

    @Transactional
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
    public Account findClientActiveAccount(Client client) {
        return accountDao.findActiveAccountByClientId(client.getId());
    }

    @Override
    public Client findClientById(long id) {
        Client client = clientDao.find(id);
        return client;
    }
    @Transactional
    @Override
    public Client updateClient(Client client) {
        client = clientDao.update(client);
        return client;
    }
    @Transactional
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
