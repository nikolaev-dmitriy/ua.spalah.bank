package main.java.ua.spalah.bank.exceptions;

/**
 * Created by Man on 08.01.2017.
 */
public class NotEnoughFundsException extends BankException {

    protected NotEnoughFundsException(String message) {
        super(message);
    }

    public NotEnoughFundsException(double available) {
        super("Not enough funds, only $" + available);
    }
}
