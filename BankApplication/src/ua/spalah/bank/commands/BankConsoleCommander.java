package ua.spalah.bank.commands;

import ua.spalah.bank.IO.ConsoleIO;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Created by Man on 31.01.2017.
 */
public class BankConsoleCommander extends BankCommander {

    public BankConsoleCommander() {
        super(new ConsoleIO());
    }
    public void run(){
        while (true) {
            ArrayList<Integer> canBeSelected = new ArrayList<>(commands.length);
            io.write("--------------------------MENU---------------------------------\n");
            if (currentClient == null) {
                for (int i = 0; i < commands.length; i++) {
                    if (!commands[i].currentClientIsNeeded()) {
                        canBeSelected.add(i+1);
                        io.write(i+1+") " + commands[i].getCommandInfo()+"\n");
                    }
                }
                io.write("Current client is not selected\n");
            } else {
                for (int i = 0; i < commands.length; i++) {
                    canBeSelected.add(i+1);
                    io.write(i + 1 + ") " + commands[i].getCommandInfo()+"\n");
                }
                io.write("Current client: " + currentClient.getName()+"\n");
            }
            try {
                io.write("Enter command number: ");
                int command = Integer.parseInt(io.read().trim());
                if (canBeSelected.contains(command)) {
                    commands[command - 1].execute();
                } else {
                    io.write("This command is not available\n");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                io.write("Wrong command number!\n");

            } catch (InputMismatchException e) {
                io.write("This is not a number!\n");
            }
        }

    }
    public static void main(String[] args) {
        BankConsoleCommander bankConsoleCommander = new BankConsoleCommander();
        bankConsoleCommander.run();
    }
}
