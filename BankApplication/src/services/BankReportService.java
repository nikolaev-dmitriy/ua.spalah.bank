package services;

import models.Bank;
import models.Client;

import java.util.ArrayList;

/**
 * Created by Man on 07.01.2017.
 */
public interface BankReportService {
    int getNumberOfClients(Bank bank); // общее количество клиентов
    int getNumberOfAccounts(Bank bank); // общее количество счетов
    double getTotalAccountSum(Bank bank); // общая сумма по всем счетам
    double getBankCreditSum(Bank bank); // возвращает сумму отрицательных балансов по всем счетам
    ArrayList<Client> getClientsSortedByName(Bank bank); // Возвращает список клиентов отсортированных по имени
}
