package ua.spalah.bank.commands;

import ua.spalah.bank.IO.IO;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.dao.impl.AccountDaoImpl;
import ua.spalah.bank.dao.impl.ClientDaoImpl;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.BankReportService;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.BankReportServiceImpl;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class BankCommander {

    // хранит в себе клиента с которым мы работаем в данный момент
    public static Client currentClient;

    // Список команд которые мы можем выполнять
    protected Command[] commands;
    protected IO io;

    public BankCommander(IO io) {
        this.io = io;
        init();
    }

    private void init() {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:tcp://localhost/D:/BankApplication/bank";
            Connection connection = DriverManager.getConnection(url, "Dmitriy", "");
            connection.setAutoCommit(false);
            ClientDao clientDao = new ClientDaoImpl(connection);
            AccountDao accountDao = new AccountDaoImpl(connection);
            ClientService clientService = new ClientServiceImpl(clientDao, accountDao);
            AccountService accountService = new AccountServiceImpl(clientDao, accountDao);
            BankReportService bankReportService = new BankReportServiceImpl(clientDao, accountDao);

            this.commands = new Command[]{
                    new FindClientCommand(io, clientService, accountService),
                    new GetAccountsCommand(clientService, accountService, io),
                    new SelectActiveAccountCommand(clientService, accountService, io),
                    new DepositCommand(accountService, clientService, io),
                    new WithdrawCommand(accountService, clientService, io),
                    new TransferCommand(accountService, clientService, io),
                    new AddClientCommand(clientService, accountService, io),
                    new AddAccountCommand(clientService, accountService, io),
                    new RemoveClientCommand(clientService, accountService, io),
                    new GetBankInfoCommand(bankReportService, clientService, accountService, io),
                    new ExitCommand(connection, io)
            };

        } catch (Exception e) {
//            throw new RuntimeException("Initialization error", e);
            RuntimeException ex = new RuntimeException("Initialization error");
            ex.initCause(e);
            throw ex;
        }
    }

    public abstract void run();

    // запуск нашего приложения

}
