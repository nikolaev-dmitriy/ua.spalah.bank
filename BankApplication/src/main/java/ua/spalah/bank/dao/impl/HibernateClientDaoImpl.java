package ua.spalah.bank.dao.impl;

import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Client;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Man on 12.03.2017.
 */
public class HibernateClientDaoImpl implements ClientDao {
    private EntityManagerFactory entityManagerFactory;

    public HibernateClientDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Client save(Client client) {
        return null;
    }

    @Override
    public Client update(Client client) {
        return null;
    }

    @Override
    public Client saveOrUpdate(Client client) {
        return null;
    }

    @Override
    public void delete(long clientId) {

    }

    @Override
    public Client find(long id) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client findByName(String name) throws ClientNotFoundException {
        return null;
    }
}
