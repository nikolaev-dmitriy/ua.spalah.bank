package services;

import exceptions.BankException;
import exceptions.ClientNotFoundException;
import models.Bank;
import models.Client;
import models.accounts.Account;

import java.util.ArrayList;

/**
 * Created by Man on 07.01.2017.
 */
public interface ClientService {
    double getTotalBalance(Client client);
    void addAccount(Client client, Account account);
    Client findClientByName(Bank bank, String name)throws ClientNotFoundException;
    ArrayList<Client> findAllClients(Bank bank)throws ClientNotFoundException;
    Client saveClient(Bank bank,Client client)throws BankException;
    void deleteClient(Bank bank,Client client);
}
