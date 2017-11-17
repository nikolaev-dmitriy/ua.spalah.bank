package ua.spalah.bank.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Man on 12.03.2017.
 */
@Repository
public class HibernateAccountDaoImpl implements AccountDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Account save(long clientId, Account account) {
        entityManager.persist(account);
        Query query = entityManager.createNativeQuery("UPDATE PUBLIC.ACCOUNTS set CLIENT_ID=:clientId where id=:accountId");
        query.setParameter("clientId", clientId);
        query.setParameter("accountId", account.getId());
        query.executeUpdate();
        return account;
    }
    @Override
    public Account saveOrUpdate(long clientId, Account account) {
        if (account.getId() != 0) {
            account = update(clientId, account);
        } else {
            account = save(clientId, account);
        }
        return account;
    }

    @Override
    public Account update(long clientId, Account account) {

            account = entityManager.merge(account);
            Query query = entityManager.createNativeQuery("UPDATE PUBLIC .ACCOUNTS SET CLIENT_ID=:clientId WHERE ID=:id");
            query.setParameter("clientId", clientId);
            query.setParameter("id", account.getId());
            query.executeUpdate();
            account = entityManager.find(Account.class, account.getId());
{}
        return account;
    }
    @Override
    public void delete(long id) {

        Account account = entityManager.find(Account.class, id);
        entityManager.remove(account);

    }

    @Override
    public Account find(long id) {

        Account account = entityManager.find(Account.class, id);

        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = entityManager.createQuery("from Account", Account.class).getResultList();
        return accounts;
    }

    @Override
    public List<Account> findByClientId(long clientId) {

        List<Account> accounts = entityManager.createNativeQuery("SELECT * FROM PUBLIC.ACCOUNTS WHERE CLIENT_ID=" + clientId, Account.class).getResultList();

        return accounts;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) {

        Query query = entityManager.createQuery("select cl from Client cl where cl.name=:name");
        query.setParameter("name", clientName);
        Client client = (Client) query.getSingleResult();

        return client.getActiveAccount();
    }

    @Override
    public Account setActiveAccount(long clientId, long id) {

        Query query = entityManager.createQuery("update Client cl set cl.activeAccount.id=:activeAccountId where cl.id=:clientId");
        query.setParameter("activeAccountId",id);
        query.setParameter("clientId",clientId);
        query.executeUpdate();
        Account account = entityManager.find(Account.class, id);

        return account;
    }

    @Override
    public void deleteByClientId(long clientId) {
    }

    @Override
    public Account findActiveAccountByClientId(long clientId) {

        Client client = entityManager.find(Client.class, clientId);

        return client.getActiveAccount();
    }
}
