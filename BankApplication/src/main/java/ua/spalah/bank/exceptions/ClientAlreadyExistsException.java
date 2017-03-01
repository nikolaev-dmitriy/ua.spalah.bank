package main.java.ua.spalah.bank.exceptions;

/**
 * Created by Man on 08.01.2017.
 */
public class ClientAlreadyExistsException extends BankException {
    public ClientAlreadyExistsException(String name) {
        super("Client " + name + " already exists");
    }
}
