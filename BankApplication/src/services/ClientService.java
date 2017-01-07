package services;

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
    Client findClientByName(Bank bank, String name);
    ArrayList<Client> findAllClients(Bank bank);
    Client saveClient(Bank bank,Client client);
    void deleteClient(Bank bank,Client client);
    String getClientInfo(Bank bank,String name);
}
