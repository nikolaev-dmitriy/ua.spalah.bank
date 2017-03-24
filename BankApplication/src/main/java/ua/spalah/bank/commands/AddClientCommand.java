//package ua.spalah.bank.commands;
//
//
//import ua.spalah.bank.IO.ConsoleIO;
//import ua.spalah.bank.IO.IO;
//import ua.spalah.bank.models.Client;
//import ua.spalah.bank.models.type.Gender;
//import ua.spalah.bank.services.AccountService;
//import ua.spalah.bank.services.ClientService;
//
///**
// * Created by Man on 12.01.2017.
// */
//public class AddClientCommand extends AbstractCommand implements Command {
//    private final ClientService clientService;
//    private final AccountService accountService;
//    private final String emailRegex = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
//    private final String phoneRegex = "^[+][0-9]{12}$";
//
//    public AddClientCommand(ClientService clientService, AccountService accountService) {
//        super(new ConsoleIO());
//        this.clientService = clientService;
//        this.accountService = accountService;
//    }
//
//    public AddClientCommand(ClientService clientService,AccountService accountService, IO io) {
//        super(io);
//        this.clientService = clientService;
//        this.accountService=accountService;
//    }
//
//    @Override
//    public void execute() {
//        write("Enter the name of client:\n");
//        String name = read().trim();
//        write("Enter the gender\n1.Male\n2.Female\n");
//        int genderInt = Integer.parseInt(read().trim());
//        Gender gender = null;
//        while (gender == null) {
//            if (genderInt == 1) {
//                gender = Gender.MALE;
//                break;
//            } else if (genderInt == 2) {
//                gender = Gender.FEMALE;
//                break;
//            } else {
//                write("Incorrect input\n");
//            }
//        }
//        String email = "";
//        String telephone = "";
//        write("Enter your city:\n");
//        String city = read().trim();
//        do {
//            write("Enter your phone number:\n");
//            telephone = read().trim();
//            if (!telephone.matches(phoneRegex)) {
//                write("Phone number " + telephone + " invalid\n");
//            }
//        } while (!telephone.matches(phoneRegex));
//        do {
//            write("Enter your e-mail:\n");
//            email = read().trim();
//            if (!email.matches(emailRegex)) {
//                write("E-mail " + email + " invalid\n");
//            }
//        } while (!email.matches(emailRegex));
//        Client client = new Client(name, gender, email, telephone, city);
//            BankCommander.currentClient = clientService.saveClient(client);
//    }
//
//    @Override
//    public String getCommandInfo() {
//        return "Registration client";
//    }
//
//    @Override
//    public boolean currentClientIsNeeded() {
//        return false;
//    }
//
//
//}
