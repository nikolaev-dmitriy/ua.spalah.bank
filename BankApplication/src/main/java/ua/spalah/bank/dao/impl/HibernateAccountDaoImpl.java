package ua.spalah.bank.dao.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.models.accounts.Account;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Man on 12.03.2017.
 */
public class HibernateAccountDaoImpl implements AccountDao {
    EntityManagerFactory entityManagerFactory;

    public HibernateAccountDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Account save(long clientId, Account account) {
        return null;
    }

    @Override
    public Account update(long clientId, Account account) {
        return null;
    }

    @Override
    public Account saveOrUpdate(long clientId, Account account) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Account find(long id) {
        return null;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public List<Account> findByClientId(long clientId) {
        return null;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) {
        return null;
    }

    @Override
    public Account setActiveAccount(long clientId, long id) {
        return null;
    }

    @Override
    public void deleteByClientId(long clientId) {

    }

    @Override
    public Account findActiveAccountByClientId(long clientId) {
        return null;
    }
}
