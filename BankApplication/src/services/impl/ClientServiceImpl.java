package services.impl;

import exceptions.BankException;
import exceptions.ClientNotFoundException;
import models.Bank;
import models.Client;
import models.accounts.Account;
import services.ClientService;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Man on 07.01.2017.
 */
public class ClientServiceImpl implements ClientService {
    @Override
    public Client findClientByName(Bank bank, String name)throws ClientNotFoundException{
        for(Client client :bank.getClients() ){
            if(client.getName().equalsIgnoreCase(name)){
                return client;
            }
        }
        throw new ClientNotFoundException("Client "+name+" not found");
    }
    @Override
    public Client saveClient(Bank bank, Client client)throws BankException{
        if(!bank.getClients().contains(client)) {
            bank.getClients().add(client);
            return client;
        }else{
            throw new BankException("Client "+client.getName() +" has already registered") ;
        }
    }
    @Override
    public ArrayList<Client> findAllClients(Bank bank)throws ClientNotFoundException {
        if(bank.getClients().size()>0){
        return bank.getClients();
        }else{
            throw new ClientNotFoundException("Database is clear");
        }
    }
    @Override
    public void deleteClient(Bank bank,Client client){
        ArrayList<Client> clients = bank.getClients();
        Iterator<Client> iterator =clients.iterator();
        while(iterator.hasNext()){
            Client deletedClient=iterator.next();
            if(deletedClient.equals(client)){
                iterator.remove();
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
}
