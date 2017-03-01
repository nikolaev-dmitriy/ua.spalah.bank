package main.java.ua.spalah.bank.dao;

import main.java.ua.spalah.bank.exceptions.ClientNotFoundException;
import main.java.ua.spalah.bank.models.Client;

import java.util.List;

/**
 * Created by Man on 09.02.2017.
 */
public interface ClientDao {

    Client save(Client client);

    // Обновляет информаци о существующем клиенте
    Client update(Client client);

    // В зависимости от того новый клиент или существующий (есть уже id или еще нет) сохраняет его или обновляет его информацию
    Client saveOrUpdate(Client client);

    // Удаляет клиента по его id
    void delete(long clientId) throws ClientNotFoundException;

    // Находит клиента по его id
    Client find(long id) throws ClientNotFoundException;

    // Достает из базы всех клиентов
    List<Client> findAll();

    // Находит клиента по имени
    Client findByName(String name) throws ClientNotFoundException;
}
