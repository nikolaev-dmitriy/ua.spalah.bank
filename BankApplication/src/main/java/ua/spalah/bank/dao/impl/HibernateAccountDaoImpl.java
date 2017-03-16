package ua.spalah.bank.dao.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        Query query = entityManager.createNativeQuery("UPDATE PUBLIC.ACCOUNTS set CLIENT_ID=:clientId where id=:accountId");
        query.setParameter("clientId", clientId);
        query.setParameter("accountId", account.getId());
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

    @Override
    public Account update(long clientId, Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        account = entityManager.merge(account);
        Query query = entityManager.createNativeQuery("UPDATE PUBLIC .ACCOUNTS SET CLIENT_ID=:clientId WHERE ID=:id");
        query.setParameter("clientId",clientId);
        query.setParameter("id",account.getId());
        query.executeUpdate();
        account = entityManager.find(Account.class, account.getId());
        entityManager.getTransaction().commit();
        entityManager.close();
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
    public void delete(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Account account = entityManager.find(Account.class, id);
        entityManager.remove(account);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Account find(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Account account = entityManager.find(Account.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

    @Override
    public List<Account> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Account> accounts = entityManager.createQuery("from Account", Account.class).getResultList();
        entityManager.close();
        return accounts;
    }

    @Override
    public List<Account> findByClientId(long clientId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Account> accounts = entityManager.createNativeQuery("SELECT * FROM PUBLIC.ACCOUNTS WHERE CLIENT_ID=" + clientId, Account.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return accounts;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select cl from Client cl where cl.name=:name");
        query.setParameter("name", clientName);
        Client client = (Client) query.getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return client.getActiveAccount();
    }

    @Override
    public Account setActiveAccount(long clientId, long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Client cl set cl.activeAccount.id=:activeAccountId where cl.id=:clientId");
        query.setParameter("activeAccountId",id);
        query.setParameter("clientId",clientId);
        query.executeUpdate();
        Account account = entityManager.find(Account.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

    @Override
    public void deleteByClientId(long clientId) {
    }

    @Override
    public Account findActiveAccountByClientId(long clientId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, clientId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return client.getActiveAccount();
    }
}
