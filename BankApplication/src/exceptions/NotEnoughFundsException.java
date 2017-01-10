package exceptions;

/**
 * Created by Man on 08.01.2017.
 */
public class NotEnoughFundsException extends BankException {
    public NotEnoughFundsException(String message) {
        super(message);
    }

    public NotEnoughFundsException() {
        super("Not enough money on account");
    }
}
