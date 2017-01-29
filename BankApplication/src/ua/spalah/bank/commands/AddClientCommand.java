package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;
import ua.spalah.bank.IO.IO;
import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 12.01.2017.
 */
public class AddClientCommand implements Command {
    private final ClientService clientService;
    private final IO io;
    private final String emailRegex = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
    private final String phoneRegex = "^[+][0-9]{12}$";

    public AddClientCommand(ClientService clientService) {
        this.clientService = clientService;
        io = new ConsoleIO();
    }

    public AddClientCommand(ClientService clientService, IO io) {
        this.clientService = clientService;
        this.io = io;
    }

    @Override
    public void execute() {
        io.write("Enter the name of client:");
        String name = io.read().trim();
        io.write("Enter the gender\n1.Male\n2.Female");
        int genderInt = Integer.parseInt(io.read().trim());
        Gender gender = null;
        io.write("Please, push Enter to continue registration");
        while (gender == null) {
            if (genderInt == 1) {
                gender = Gender.MALE;
                break;
            } else if (genderInt == 2) {
                gender = Gender.FEMALE;
                break;
            } else {
                io.write("Incorrect input");
            }
        }
        String email = "";
        String telephone = "";
        io.read().trim();
        io.write("Enter your city:");
        String city = io.read().trim();
        do {
            io.write("Enter your phone number:");
            telephone = io.read().trim();
            if (!telephone.matches(phoneRegex)) {
                io.write("Phone number " + telephone + " invalid");
            }
        } while (!telephone.matches(phoneRegex));
        do {
            io.write("Enter your e-mail:");
            email = io.read().trim();
            if (!email.matches(emailRegex)) {
                io.write("E-mail " + email + " invalid");
            }
        } while (!email.matches(emailRegex));
        Client client = new Client(name, gender, email, telephone, city);
        try {
            clientService.saveClient(BankCommander.currentBank, client);
            BankCommander.currentClient = client;
            AddAccountCommand addAccountCommand = new AddAccountCommand(clientService);
            addAccountCommand.execute();
        } catch (ClientAlreadyExistsException e) {
            io.write(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Registration client";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return false;
    }


}
