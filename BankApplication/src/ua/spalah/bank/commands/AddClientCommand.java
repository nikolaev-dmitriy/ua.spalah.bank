package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Man on 12.01.2017.
 */
public class AddClientCommand implements Command {
    private final ClientService clientService;
    private final String emailRegex = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
    private final String phoneRegex = "^[+][0-9]{12}$";

    public AddClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of client:");
        String name = in.nextLine();
        System.out.println("Enter the gender\n1.Male\n2.Female");
        int genderInt = in.nextInt();
        Gender gender = null;
        do {
            if (genderInt == 1) {
                gender = Gender.MALE;
                break;
            } else if (genderInt == 2) {
                gender = Gender.FEMALE;
                break;
            } else {
                System.out.println("Incorrect input");
            }
        } while (genderInt != 1 || genderInt != 2);
        String email = "";
        String telephone = "";
        in.nextLine();
        System.out.println("Enter your city:");
        String city = in.nextLine();
        do {
            System.out.println("Enter your phone number:");
            telephone = in.nextLine();
            if (!telephone.matches(phoneRegex)) {
                System.out.println("Phone number " + telephone + " invalid");
            }
        } while (!telephone.matches(phoneRegex));
        do {
            System.out.println("Enter your e-mail:");
            email = in.nextLine();
            if (!email.matches(emailRegex)) {
                System.out.println("E-mail " + email + " invalid");
            }
        } while (!email.matches(emailRegex));
        Client client = new Client(name, gender, email, telephone, city);
        try {
            clientService.saveClient(BankCommander.currentBank, client);
            BankCommander.currentClient = client;
            AddAccountCommand addAccountCommand = new AddAccountCommand(clientService);
            addAccountCommand.execute();
        } catch (ClientAlreadyExistsException e) {
            System.out.println(e.getMessage());
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
