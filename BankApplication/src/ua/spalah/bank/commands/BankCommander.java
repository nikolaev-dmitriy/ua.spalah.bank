package ua.spalah.bank.commands;

import ua.spalah.bank.IO.IO;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.AccountType;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.BankReportService;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.BankReportServiceImpl;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Kostiantyn Huliaiev
 */
public abstract class BankCommander {
    // хранит в себе банк с кототорым мы работаем
    public static Bank currentBank;

    // хранит в себе клиента с которым мы работаем в данный момент
    public static Client currentClient;

    // Список команд которые мы можем выполнять
    protected Command[] commands;
    protected IO io;

    public BankCommander(IO io) {
        this.io=io;
        init();
    }

    private void init() {
        try {

            ClientService clientService = new ClientServiceImpl();
            AccountService accountService = new AccountServiceImpl();
            BankReportService bankReportService = new BankReportServiceImpl();
            Bank bank = new Bank();
            Path bankCommanderPath = Paths.get(BankCommander.class.getResource("").toURI());
            Path clientsPath = bankCommanderPath.resolve("../../../../../../../BankApplication/resources/clients.txt");
            List<String> clientsStrings = Files.readAllLines(clientsPath.normalize());
            for (String clientsString : clientsStrings) {
                String[] clientInfo = clientsString.split("::");
                String name = clientInfo[0];
                Gender gender = null;
                switch (clientInfo[1]) {
                    case "MALE": {
                        gender = Gender.MALE;
                        break;
                    }
                    case "FEMALE": {
                        gender = Gender.FEMALE;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }
                }
                String email = clientInfo[2];
                String number = clientInfo[3];
                String city = clientInfo[4];
                Client client = new Client( name, gender, email, number, city);
                bank.getClients().put(client.getName(), client);
            }
            Path accountsPath = clientsPath.resolveSibling("accounts.txt");
            List<String> accountsStrings = Files.readAllLines(accountsPath);
            for (String accountsString : accountsStrings) {
                String[] accountInfo = accountsString.split("::");
                Client client = clientService.findClientByName(bank, accountInfo[0]);
                Account account = null;
                double balance = Double.parseDouble(accountInfo[2]);
                double overdraft = 0;
                if (accountInfo.length == 4) {
                    overdraft = Double.parseDouble(accountInfo[3]);
                }
                AccountType accountType = null;
                switch (accountInfo[1]) {
                    case "SAVING": {
                        account = new SavingAccount(balance);
                        break;
                    }
                    case "CHECKING": {
                        account = new CheckingAccount(balance, overdraft);
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }
                }
                client.getAccounts().add(account);
                client.setActiveAccount(account);

            }
            currentBank = bank;

            this.commands = new Command[]{
                    new FindClientCommand(io,clientService),
                    new GetAccountsCommand(io),
                    new SelectActiveAccountCommand(io),
                    new DepositCommand(accountService,io),
                    new WithdrawCommand(accountService,io),
                    new TransferCommand(accountService, clientService,io),
                    new AddClientCommand(clientService,io),
                    new AddAccountCommand(clientService,io),
                    new RemoveClientCommand(clientService,io),
                    new GetBankInfoCommand(bankReportService,io),
                    new ExitCommand(io)
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
