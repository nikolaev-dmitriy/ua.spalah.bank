package main.java.ua.spalah.bank.dao.impl;

import main.java.ua.spalah.bank.dao.AccountDao;
import main.java.ua.spalah.bank.dao.ClientDao;
import main.java.ua.spalah.bank.exceptions.ClientNotFoundException;
import main.java.ua.spalah.bank.exceptions.DataBaseException;
import main.java.ua.spalah.bank.models.accounts.Account;
import main.java.ua.spalah.bank.models.accounts.CheckingAccount;
import main.java.ua.spalah.bank.models.accounts.SavingAccount;
import main.java.ua.spalah.bank.models.type.AccountType;
import org.h2.jdbc.JdbcSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Man on 09.02.2017.
 */
public class AccountDaoImpl implements AccountDao {
    private Connection connection;


    public AccountDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account save(long clientId, Account account) {
        try {
            PreparedStatement preparedStatement = null;
            if (account.getType() == AccountType.CHECKING) {
                String sql = "INSERT INTO PUBLIC.ACCOUNTS (client_id,account_type,balance,overdraft) values (?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(2, account.getType().name());
                preparedStatement.setDouble(3, account.getBalance());
                preparedStatement.setDouble(4, ((CheckingAccount) account).getOverdraft());
                preparedStatement.setLong(1, clientId);
            } else {
                String sql = "INSERT INTO PUBLIC.ACCOUNTS (client_id,account_type,balance) values (?,?,?)";
                preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(2, account.getType().name());
                preparedStatement.setDouble(3, account.getBalance());
                preparedStatement.setLong(1, clientId);
            }
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            account.setId(generatedKeys.getLong(1));
            connection.commit();
            preparedStatement.close();
            return account;
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Account update(long clientId, Account account) {
        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC.ACCOUNTS SET  ACCOUNT_TYPE=?,BALANCE=?,OVERDRAFT=?,CLIENT_ID=? WHERE id=?");
                preparedStatement.setDouble(3, ((CheckingAccount) account).getOverdraft());

                preparedStatement.setString(1, account.getType().name());
                preparedStatement.setDouble(2, account.getBalance());
                if (clientId != 0) {
                    preparedStatement.setLong(4, clientId);
                } else {
                    preparedStatement.setNull(4, 0);
                }
                preparedStatement.setLong(5, account.getId());
                preparedStatement.executeUpdate();
                connection.commit();
                preparedStatement.close();
                return account;
            } catch (ClassCastException e) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC.ACCOUNTS SET  ACCOUNT_TYPE=?,BALANCE=?,CLIENT_ID=? WHERE id=?");

                preparedStatement.setString(1, account.getType().name());
                preparedStatement.setDouble(2, account.getBalance());
                if (clientId != 0) {
                    preparedStatement.setLong(3, clientId);
                } else {
                    preparedStatement.setNull(3, 0);
                }
                preparedStatement.setLong(4, account.getId());
                preparedStatement.executeUpdate();
                connection.commit();
                preparedStatement.close();
                return account;
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }


    @Override
    public Account saveOrUpdate(long clientId, Account account) {
        if (account.getId() == 0) {
            account = save(clientId, account);
        } else {
            account = update(clientId, account);
        }
        return account;
    }

    @Override
    public void delete(long id) {
        try {
            Account account = find(id);
            account = update(0, account);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.ACCOUNTS where id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

    }

    @Override
    public Account find(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();


                if (resultSet.getString("account_type").equals("CHECKING")) {
                    Account account = new CheckingAccount(resultSet.getDouble("balance"), resultSet.getDouble("overdraft"));
                    account.setId(resultSet.getLong("id"));
                    preparedStatement.close();
                    return account;
                } else {
                    Account account = new SavingAccount(resultSet.getDouble("balance"));
                    account.setId(resultSet.getLong("id"));
                    preparedStatement.close();
                    return account;
                }

        } catch (JdbcSQLException e) {
            return null;
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList = new ArrayList<Account>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from public.accounts order by ID";
            ResultSet resultSet = statement.executeQuery(sql);
            accountList = addToListMethod(accountList, resultSet);
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return accountList;
    }

    @Override
    public List<Account> findByClientId(long clientId) {
        List<Account> accountListByClientId = new ArrayList<Account>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE client_id = ? ORDER BY ID");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            accountListByClientId = addToListMethod(accountListByClientId, resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return accountListByClientId;
    }

    private static List<Account> addToListMethod(List<Account> accountListByClientId, ResultSet resultSet) throws SQLException {
        Account account = null;
        while (resultSet.next()) {
            if (resultSet.getString("account_type").equals("SAVING")) {
                account = new SavingAccount(resultSet.getDouble("balance"));
                account.setId(resultSet.getLong("id"));
                accountListByClientId.add(account);
            } else {
                account = new CheckingAccount(resultSet.getDouble("balance"), resultSet.getDouble("overdraft"));
                account.setId(resultSet.getLong("id"));
                accountListByClientId.add(account);
            }
        }
        return accountListByClientId;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) throws ClientNotFoundException {
        String findAccountId = "SELECT ACTIVE_ACCOUNT_ID FROM PUBLIC.CLIENTS WHERE NAME = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findAccountId);
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long id = resultSet.getInt("ACTIVE_ACCOUNT_ID");
            preparedStatement.close();
            return find(id);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Account setActiveAccount(long clientId, long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC.CLIENTS SET ACTIVE_ACCOUNT_ID=? WHERE ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, clientId);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            return find(id);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

    }

    @Override
    public void deleteByClientId(long clientId) {
       /* try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PUBLIC .ACCOUNTS WHERE CLIENT_ID=?");
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }*/

    }

    @Override
    public Account findActiveAccountByClientId(long clientId) throws ClientNotFoundException {
        ClientDao clientDao = new ClientDaoImpl(connection);
        return clientDao.find(clientId).getActiveAccount();
    }


}
