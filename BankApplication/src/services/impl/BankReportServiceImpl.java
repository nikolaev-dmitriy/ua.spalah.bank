package services.impl;

import models.Bank;
import models.Client;
import models.accounts.Account;
import models.accounts.AccountType;
import models.accounts.CheckingAccount;
import services.BankReportService;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Man on 07.01.2017.
 */
public class BankReportServiceImpl implements BankReportService {
    @Override
    public int getNumberOfClients(Bank bank){
        return bank.getClients().size();
    }
    @Override
    public int getNumberOfAccounts(Bank bank){
        int k=0;
        for(Client client:bank.getClients()){
            k+=client.getAccounts().size();
        }
        return k;
    }
    @Override
    public double getTotalAccountSum(Bank bank){
        double totalSum=0;
        for(Client client: bank.getClients()){
            for(Account account: client.getAccounts()){
                totalSum += account.getBalance();
            }
        }
        return totalSum;
    }
    @Override
    public double getBankCreditSum(Bank bank){
        double totalCredit=0;
        for(Client client : bank.getClients() ){
            for(Account account : client.getAccounts() ){
                if (account.getAccountType().equals(AccountType.CHECKING)){
                    CheckingAccount checkingAccount =(CheckingAccount) account;
                    totalCredit +=checkingAccount.getOverdraft();
                }
            }
        }
        return totalCredit;
    }
    @Override
    public ArrayList<Client> getClientsSortedByName(Bank bank){
        ArrayList<Client> clients =bank.getClients();
        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client client1,Client client2){
                return client1.getName().compareTo(client2.getName());
            }
        });
        return clients;
    }
}
