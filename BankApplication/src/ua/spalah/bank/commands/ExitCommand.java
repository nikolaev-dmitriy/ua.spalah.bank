package ua.spalah.bank.commands;

import ua.spalah.bank.IO.IO;

/**
 * Created by Man on 12.01.2017.
 */
public class ExitCommand extends AbstractCommand implements Command {

    public ExitCommand(IO io) {
        super(io);
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String getCommandInfo() {
        return "Exit";
    }

    @Override
    public boolean currentClientIsNeeded() {
        return false;
    }


}
