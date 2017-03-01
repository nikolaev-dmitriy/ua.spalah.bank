package main.java.ua.spalah.bank.services;

import main.java.ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import main.java.ua.spalah.bank.exceptions.ClientNotFoundException;
import main.java.ua.spalah.bank.models.Client;
import main.java.ua.spalah.bank.models.accounts.Account;

import java.util.List;

/**
 * Created by Man on 07.01.2017.
 */
public interface ClientService {
    double getTotalBalance(Client client);

    Client findClientByName(String name) throws ClientNotFoundException;

    Client saveClient(Client client) throws ClientAlreadyExistsException;

    List<Client> findAllClients();

    void deleteClient(String name) throws ClientNotFoundException;

    List<Account> getClientAccounts (Client client);

    Account findClientActiveAccount(Client Client) throws ClientNotFoundException;

}
