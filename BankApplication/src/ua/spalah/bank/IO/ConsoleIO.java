package ua.spalah.bank.IO;

import java.util.Scanner;

/**
 * Created by Man on 29.01.2017.
 */
public class ConsoleIO implements IO {
    @Override
    public String read() {
        Scanner in = new Scanner(System.in);
    return in.nextLine();
    }

    @Override
    public void write(String s) {
        System.out.println(s);
    }
}
