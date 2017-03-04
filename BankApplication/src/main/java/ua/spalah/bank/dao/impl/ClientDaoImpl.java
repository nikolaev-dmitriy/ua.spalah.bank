package ua.spalah.bank.dao.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.DataBaseException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Man on 09.02.2017.
 */
public class ClientDaoImpl implements ClientDao {
    private Connection connection;

    public ClientDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client save(Client client) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PUBLIC.CLIENTS (name,gender,email,telephone,city,ACTIVE_ACCOUNT_ID) VALUES (?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getName());
            statement.setString(2, client.getGender().name());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getTelephone());
            statement.setString(5, client.getCity());
            if (client.getActiveAccount() != null) {
                statement.setLong(6, client.getActiveAccount().getId());
            } else {
                statement.setNull(6, 0);
            }
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            client.setId(generatedKeys.getLong(1));
            connection.commit();
            statement.close();
            return client;
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Client update(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC.CLIENTS SET name=?,gender=?,email=?,telephone=?,city=?,ACTIVE_ACCOUNT_ID=? WHERE id=?");
            preparedStatement.setLong(7, client.getId());
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender().name());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getCity());
            if (client.getActiveAccount() == null) {
                preparedStatement.setNull(6, 0);
            } else {
                preparedStatement.setLong(6, client.getActiveAccount().getId());
            }
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            return client;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DataBaseException(e);
        }
    }

    @Override
    public Client saveOrUpdate(Client client) {
        if (client.getId()!=0) {
            client = update(client);
        } else {
            client = save(client);
        }
        return client;
    }

    @Override
    public void delete(long clientId) throws ClientNotFoundException {
        try {
            Client client = find(clientId);
            client.setActiveAccount(null);
            client = update(client);
            AccountDao accountDao = new AccountDaoImpl(connection);
            List<Account> clientAccounts = accountDao.findByClientId(client.getId());
            for (Account account : clientAccounts) {
                accountDao.delete(account.getId());
            }
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PUBLIC.CLIENTS WHERE ID=?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Client find(long id) throws ClientNotFoundException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"), Gender.valueOf(resultSet.getString("gender")), resultSet.getString("email"), resultSet.getString("telephone"), resultSet.getString("city"));
            return client;
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = new ArrayList<Client>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PUBLIC.CLIENTS ORDER BY ID");
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"), Gender.valueOf(resultSet.getString("gender")), resultSet.getString("email"), resultSet.getString("telephone"), resultSet.getString("city"));
                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    @Override
    public Client findByName(String name) throws ClientNotFoundException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE NAME=?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.wasNull()) {
                resultSet.next();
                Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"), Gender.valueOf(resultSet.getString("gender")), resultSet.getString("email"), resultSet.getString("telephone"), resultSet.getString("city"));
                return client;
            } else {
                throw new ClientNotFoundException(name);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

}
