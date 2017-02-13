package ua.spalah.bank.dao.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.exceptions.ClientNotFoundException;
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

    public Connection getConnection() {
        return connection;
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
            if (client.getActiveAccountId() != 0) {
                statement.setLong(6, client.getActiveAccountId());
            } else {
                statement.setNull(6, 0);
            }
            statement.executeUpdate();
            connection.commit();
            try {
                client = findByName(client.getName());
            } catch (ClientNotFoundException e) {
                connection.rollback();
            }
            statement.close();
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client update(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC.CLIENTS SET name=?,gender=?,email=?,telephone=?,city=?,ACTIVE_ACCOUNT_ID=? WHERE id=?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(7, client.getId());
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender().name());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getCity());
            if (client.getActiveAccountId() == 0) {
                preparedStatement.setNull(6, 0);
            } else {
                preparedStatement.setLong(6, client.getActiveAccountId());
            }
            preparedStatement.executeUpdate();
            connection.commit();
            client = find(client.getId());
            AccountDao accountDao = new AccountDaoImpl(connection);
            if (client.getActiveAccountId() != 0) {
                client.setActiveAccount(accountDao.find(client.getActiveAccountId()));
            }
            preparedStatement.close();
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client saveOrUpdate(Client client) {
        if (findAll().contains(client)) {
            client = update(client);
        } else {
            client = save(client);
        }
        return client;
    }

    @Override
    public void delete(long clientId) {
        try {
            Client client = find(clientId);
            client.setActiveAccountId(0);
            client = update(client);
            AccountDao accountDao = new AccountDaoImpl(connection);
            List<Account> clientAccounts = accountDao.findByClientId(client.getId());
            for (Account account : clientAccounts) {
                accountDao.delete(account.getId());
            }
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PUBLIC.CLIENTS WHERE ID=?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, client.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client find(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"), Gender.valueOf(resultSet.getString("gender")), resultSet.getString("email"), resultSet.getString("telephone"), resultSet.getString("city"));
            if (resultSet.getObject("active_account_id") == null) {
                client.setActiveAccountId(0);
            } else {
                client.setActiveAccountId(resultSet.getLong("active_account_id"));
                AccountDao accountDao = new AccountDaoImpl(connection);
                client.setActiveAccount(accountDao.find(client.getActiveAccountId()));
            }
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = new ArrayList<Client>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PUBLIC.CLIENTS ORDER BY ID");
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"), Gender.valueOf(resultSet.getString("gender")), resultSet.getString("email"), resultSet.getString("telephone"), resultSet.getString("city"));
                if (resultSet.getObject("active_account_id") == null) {
                    client.setActiveAccountId(0);
                } else {
                    client.setActiveAccountId(resultSet.getLong("active_account_id"));
                }
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE NAME=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.wasNull()) {
                resultSet.next();
                Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"), Gender.valueOf(resultSet.getString("gender")), resultSet.getString("email"), resultSet.getString("telephone"), resultSet.getString("city"));
                if (resultSet.getObject("active_account_id") == null) {
                    client.setActiveAccountId(0);
                } else {
                    client.setActiveAccountId(resultSet.getLong("active_account_id"));
                    AccountDao accountDao = new AccountDaoImpl(connection);
                    client.setActiveAccount(accountDao.find(client.getActiveAccountId()));
                    List<Account> accounts = accountDao.findByClientId(client.getId());
                    for (Account account : accounts) {
                        client.getAccounts().add(account);
                    }
                }
                return client;
            } else {
                throw new ClientNotFoundException(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
