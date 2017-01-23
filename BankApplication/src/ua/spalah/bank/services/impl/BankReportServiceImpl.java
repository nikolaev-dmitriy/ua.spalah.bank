package ua.spalah.bank.services.impl;

import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.type.AccountType;
import ua.spalah.bank.services.BankReportService;

import java.util.*;

/**
 * Created by Man on 07.01.2017.
 */
public class BankReportServiceImpl implements BankReportService {
    @Override
    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    @Override
    public int getNumberOfAccounts(Bank bank) {
        int k = 0;
        for (Client client : bank.getClients().values()) {
            k += client.getAccounts().size();
        }
        return k;
    }

    @Override
    public double getTotalAccountSum(Bank bank) {
        double totalSum = 0;
        for (Client client : bank.getClients().values()) {
            for (Account account : client.getAccounts()) {
                totalSum += account.getBalance();
            }
        }
        return totalSum;
    }

    @Override
    public double getBankCreditSum(Bank bank) {
        double totalCredit = 0;
        for (Client client : bank.getClients().values()) {
            for (Account account : client.getAccounts()) {
                if (account.getType().equals(AccountType.CHECKING)) {
                    CheckingAccount checkingAccount = (CheckingAccount) account;
                    totalCredit += checkingAccount.getOverdraft();
                }
            }
        }
        return totalCredit;
    }

    @Override
    public Map<String,Client> getClientsSortedByName(Bank bank) {
        //List<Client> clients = new ArrayList<Client>(bank.getClients().values());
        //clients.sort(new Comparator<Client>() {
          //  @Override
            //public int compare(Client client1, Client client2) {
              //  return client1.getName().compareTo(client2.getName());
            //}
        //});
        return new TreeMap<>(bank.getClients());
    }

    @Override
    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        Map<String, List<Client>> clientsByCity = new HashMap<>();
        for (Client client : bank.getClients().values()) {
            if (clientsByCity.containsKey(client.getCity())) {
                clientsByCity.get(client.getCity()).add(client);
            } else {
                clientsByCity.put(client.getCity(), new ArrayList<>());
                clientsByCity.get(client.getCity()).add(client);
            }
        }
        return clientsByCity;
    }
}
