package ua.spalah.bank.dao.impl;

import org.springframework.stereotype.Repository;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.models.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Man on 12.03.2017.
 */
@Repository
public class HibernateClientDaoImpl implements ClientDao {
   @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Client save(Client client) {

        entityManager.persist(client);

        return client;
    }

    @Override
    public Client update(Client client) {

        client = entityManager.merge(client);

        return client;
    }

    @Override
    public Client saveOrUpdate(Client client) {
        if (client.getId()==0) {
            client = save(client);
        } else {
            client = update(client);
        }
        return client;
    }

    @Override
    public void delete(long clientId) {

        Client client = entityManager.find(Client.class, clientId);
        entityManager.remove(client);

    }

    @Override
    public Client find(long id) {

        Client client = entityManager.find(Client.class,id);

        return client;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = entityManager.createQuery("from Client", Client.class).getResultList();
        return clients;
    }

    @Override
    public Client findByName(String name){
        Client client = entityManager.createQuery("select cl from Client cl where cl.name=" + name, Client.class).getSingleResult();
        return client;
    }
}
