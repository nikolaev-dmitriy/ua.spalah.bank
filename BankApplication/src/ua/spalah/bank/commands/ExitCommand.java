package ua.spalah.bank.commands;

/**
 * Created by Man on 12.01.2017.
 */
public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String getCommandInfo() {
        return "Exit";
    }
}
