package ua.spalah.bank.services;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

import java.util.Map;

/**
 * Created by Man on 07.01.2017.
 */
public interface ClientService {
    double getTotalBalance(Client client);
    void addAccount(Client client, Account account);
    Client findClientByName(Bank bank, String name) throws ClientNotFoundException;
    Map<String,Client> findAllClients(Bank bank);
    Client saveClient(Bank bank, Client client) throws ClientAlreadyExistsException;
    void deleteClient(Bank bank, String name);
}
