package ua.spalah.bank.commands;

/**
 * @author Kostiantyn Huliaiev
 */
public interface Command {
    void execute();
    String getCommandInfo();
    boolean currentClientIsNeeded();
}
