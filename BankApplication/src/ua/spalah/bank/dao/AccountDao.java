package ua.spalah.bank.dao;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.accounts.Account;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Man on 09.02.2017.
 */
public interface AccountDao {
    Connection getConnection();
    Account save(Account Account);

    Account update(Account Account);

    Account saveOrUpdate(Account Account);

    void delete(long id);

    Account find(long id);

    List<Account> findAll();

    List<Account> findByClientId(long clientId);

    Account findActiveAccountByClientName(String clientName) throws ClientNotFoundException;

    Account setActiveAccount(long id);
}
