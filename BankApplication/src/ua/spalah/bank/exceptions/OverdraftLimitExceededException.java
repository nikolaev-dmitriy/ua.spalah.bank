package ua.spalah.bank.exceptions;

/**
 * Created by Man on 08.01.2017.
 */
public class OverdraftLimitExceededException extends NotEnoughFundsException {

    public OverdraftLimitExceededException(double available) {
        super("You exceeded your overdraft only $" + available);
    }
}
