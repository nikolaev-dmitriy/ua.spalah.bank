package exceptions;

/**
 * Created by Man on 08.01.2017.
 */
public class OverdraftLimitExceededException extends NotEnoughFundsException {
    public OverdraftLimitExceededException() {
        super("Overdraft limit is exceeded");
    }
}
