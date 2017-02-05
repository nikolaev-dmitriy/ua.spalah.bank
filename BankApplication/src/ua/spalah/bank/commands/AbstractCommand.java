package ua.spalah.bank.commands;

import ua.spalah.bank.IO.IO;

/**
 * Created by Man on 05.02.2017.
 */
public class AbstractCommand {
    private final IO io;

    public AbstractCommand(IO io) {
        this.io = io;
    }

    public IO getIo() {
        return io;
    }

    protected String read() {
        return io.read();
    }

    protected void write(String line) {
        io.write(line);
    }
}
