package ua.spalah.bank.services.impl;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.ClientService;

import java.util.Map;

/**
 * Created by Man on 07.01.2017.
 */
public class ClientServiceImpl implements ClientService {
    @Override
    public Client findClientByName(Bank bank, String name) throws ClientNotFoundException {
        for (Client client : bank.getClients().values()) {
            if (client.getName().equalsIgnoreCase(name)) {
                return client;
            }
        }
        throw new ClientNotFoundException(name);
    }

    @Override
    public Client saveClient(Bank bank, Client client) throws ClientAlreadyExistsException {
        if (!bank.getClients().values().contains(client)) {
            bank.getClients().put(client.getName(), client) ;
            return client;
        } else {
            throw new ClientAlreadyExistsException(client.getName());
        }
    }

    @Override
    public Map<String,Client> findAllClients(Bank bank) {
        return bank.getClients();
    }

    @Override
    public void deleteClient(Bank bank, Client client) {
        bank.getClients().remove(client);
    }

    @Override
    public double getTotalBalance(Client client) {
        double totalSum = 0;
        for (Account account : client.getAccounts()) {
            totalSum += account.getBalance();
        }
        return totalSum;
    }

    @Override
    public void addAccount(Client client, Account account) {
        if (client.getAccounts().size() == 0) {
            client.setActiveAccount(account);
        }
        client.getAccounts().add(account);
    }
}
