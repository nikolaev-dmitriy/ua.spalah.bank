package ua.spalah.bank.exceptions;

/**
 * Created by Man on 20.02.2017.
 */
public class DataBaseException extends RuntimeException {
    public DataBaseException() {
    }

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }
}
