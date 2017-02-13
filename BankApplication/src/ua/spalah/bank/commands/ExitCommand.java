package ua.spalah.bank.commands;

import ua.spalah.bank.IO.IO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Man on 12.01.2017.
 */
public class ExitCommand extends AbstractCommand implements Command {
    private Connection connection;
    public ExitCommand(Connection connection, IO io) {
        super(io);
        this.connection = connection;
    }

    @Override
    public void execute() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
