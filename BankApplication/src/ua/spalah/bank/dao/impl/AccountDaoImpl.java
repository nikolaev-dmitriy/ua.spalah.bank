package ua.spalah.bank.dao.impl;

import ua.spalah.bank.commands.BankCommander;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.AccountType;

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

    public Connection getConnection() {
        return connection;
    }

    @Override
    public Account save(Account account) {
        try {
            PreparedStatement preparedStatement = null;
            if (account.getType() == AccountType.CHECKING) {
                String sql = "INSERT INTO PUBLIC.ACCOUNTS (client_id,account_type,balance,overdraft) values (?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(2, account.getType().name());
                preparedStatement.setDouble(3, account.getBalance());
                preparedStatement.setDouble(4, ((CheckingAccount) account).getOverdraft());
                preparedStatement.setLong(1, account.getClientId());
            } else {
                String sql = "INSERT INTO PUBLIC.ACCOUNTS (client_id,account_type,balance) values (?,?,?)";
                preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(2, account.getType().name());
                preparedStatement.setDouble(3, account.getBalance());
                preparedStatement.setLong(1, account.getClientId());
            }
            preparedStatement.executeUpdate();
            connection.commit();
            account = findByClientId(account.getClientId()).get(findByClientId(account.getClientId()).size() - 1);
            preparedStatement.close();
            BankCommander.currentClient.getAccounts().add(account);
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account update(Account account) {
        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC.ACCOUNTS SET  ACCOUNT_TYPE=?,BALANCE=?,OVERDRAFT=?,CLIENT_ID=? WHERE id=?", PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setDouble(3, ((CheckingAccount) account).getOverdraft());

                preparedStatement.setString(1, account.getType().name());
                preparedStatement.setDouble(2, account.getBalance());
                if (account.getClientId() != 0) {
                    preparedStatement.setLong(4, account.getClientId());
                } else {
                    preparedStatement.setNull(4, 0);
                }
                preparedStatement.setLong(5, account.getId());
                preparedStatement.executeUpdate();
                connection.commit();
                account = find(account.getId());
                preparedStatement.close();
                return account;
            } catch (ClassCastException e) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC.ACCOUNTS SET  ACCOUNT_TYPE=?,BALANCE=?,CLIENT_ID=? WHERE id=?", PreparedStatement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, account.getType().name());
                preparedStatement.setDouble(2, account.getBalance());
                if (account.getClientId() != 0) {
                    preparedStatement.setLong(3, account.getClientId());
                } else {
                    preparedStatement.setNull(3, 0);
                }
                preparedStatement.setLong(4, account.getId());
                preparedStatement.executeUpdate();
                connection.commit();
                account = find(account.getId());
                preparedStatement.close();
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account saveOrUpdate(Account account) {
        if (findAll().contains(account)) {
            account = update(account);
        } else {
            account = save(account);
        }
        ClientDao clientDao = new ClientDaoImpl(connection);
        return account;

    }

    @Override
    public void delete(long id) {
        try {
            Account account = find(id);
            account.setClientId(0);
            account = update(account);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.ACCOUNTS where id=?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Account find(long id) {
        Account account = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getString("account_type").equals("CHECKING")) {
                account = new CheckingAccount(resultSet.getLong("client_id"), resultSet.getDouble("balance"), resultSet.getDouble("overdraft"));
                account.setId(resultSet.getLong("id"));
            } else {
                account = new SavingAccount(resultSet.getLong("client_id"), resultSet.getDouble("balance"));
                account.setId(resultSet.getLong("id"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return accountListByClientId;
    }

    private static List<Account> addToListMethod(List<Account> accountListByClientId, ResultSet resultSet) throws SQLException {
        Account account = null;
        while (resultSet.next()) {
            if (resultSet.getString("account_type").equals("SAVING")) {
                account = new SavingAccount(resultSet.getLong("client_id"), resultSet.getDouble("balance"));
                account.setId(resultSet.getLong("id"));
                accountListByClientId.add(account);
            } else {
                account = new CheckingAccount(resultSet.getLong("client_id"), resultSet.getDouble("balance"), resultSet.getDouble("overdraft"));
                account.setId(resultSet.getLong("id"));
                accountListByClientId.add(account);
            }
        }
        return accountListByClientId;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) throws ClientNotFoundException {
        ClientDao clientDao = new ClientDaoImpl(connection);
        return clientDao.findByName(clientName).getActiveAccount();
    }

    @Override
    public Account setActiveAccount(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PUBLIC .CLIENTS SET ACTIVE_ACCOUNT_ID=? WHERE ID = ? ORDER BY ID",PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1,id);
            preparedStatement.setLong(2,find(id).getClientId());
            preparedStatement.executeUpdate();
            ClientDao clientDao = new ClientDaoImpl(connection);
            clientDao.update(clientDao.find(find(id).getClientId()));
            return find(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
