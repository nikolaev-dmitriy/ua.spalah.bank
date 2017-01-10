package exceptions;

/**
 * Created by Man on 08.01.2017.
 */
public class ClientNotFoundException extends BankException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
