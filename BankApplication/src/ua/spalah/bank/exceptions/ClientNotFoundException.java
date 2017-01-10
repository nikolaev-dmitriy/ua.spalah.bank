package ua.spalah.bank.exceptions;

/**
 * Created by Man on 08.01.2017.
 */
public class ClientNotFoundException extends BankException {
    public ClientNotFoundException(String name) {
        super("Client " + name + " not found");
    }
}
