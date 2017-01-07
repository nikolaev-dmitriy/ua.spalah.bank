package services.impl;

import models.Bank;
import models.Client;
import models.accounts.Account;
import services.ClientService;

import java.util.ArrayList;

/**
 * Created by Man on 07.01.2017.
 */
public class ClientServiceImpl implements ClientService {
    @Override
    public Client findClientByName(Bank bank, String name){
        ArrayList<Client> clients=bank.getClients();
        for(Client client :clients ){
            if(client.getName().equalsIgnoreCase(name)){
                return client;
            }
        }
        return null;
    }
    @Override
    public Client saveClient(Bank bank, Client client){
        bank.getClients().add(client);
        return client;
    }
    @Override
    public ArrayList<Client> findAllClients(Bank bank) {
        return bank.getClients();
    }
    @Override
    public void deleteClient(Bank bank,Client client){
        ArrayList<Client> clients = bank.getClients();
        for(Client deletedClient:clients){
            if(deletedClient.equals(client)){
                clients.remove(deletedClient);
            }
        }
    }
    @Override
    public double getTotalBalance(Client client ){
        double totalSum=0;
        for(Account account : client.getAccounts()){
            totalSum += account.getBalance();
        }
        return totalSum;
    }
    @Override
    public void addAccount(Client client, Account account){
        if(client.getAccounts().size() ==0){
            client.setActiveAccount(account);
        }
            client.getAccounts().add(account);
    }
    @Override
    public String getClientInfo(Bank bank,String name){
    for(Client client : bank.getClients()){
        if(client.getName().equalsIgnoreCase(name)){
            return client.toString();
        }
    }
    return null;
    }
}
