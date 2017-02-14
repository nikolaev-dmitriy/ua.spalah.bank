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

/**
 * @author Kostiantyn Huliaiev
 */
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
            String url = "jdbc:h2:tcp://localhost/D:/BankApplication/BankApplication/db/bank";
            Connection connection = DriverManager.getConnection(url, "Dmitriy", "");
            connection.setAutoCommit(false);
            ClientDao clientDao = new ClientDaoImpl(connection);
            AccountDao accountDao = new AccountDaoImpl(connection);
            ClientService clientService = new ClientServiceImpl(clientDao);
            AccountService accountService = new AccountServiceImpl(accountDao);
            BankReportService bankReportService = new BankReportServiceImpl(clientDao, accountDao);

            this.commands = new Command[]{
                    new FindClientCommand(io, clientService),
                    new GetAccountsCommand(clientService, io),
                    new SelectActiveAccountCommand(clientService, io),
                    new DepositCommand(accountService, io),
                    new WithdrawCommand(accountService, io),
                    new TransferCommand(accountService, clientService, io),
                    new AddClientCommand(clientService, io),
                    new AddAccountCommand(clientService, io),
                    new RemoveClientCommand(clientService, io),
                    new GetBankInfoCommand(bankReportService,clientService, io),
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
