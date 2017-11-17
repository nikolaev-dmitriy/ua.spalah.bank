package ua.spalah.bank.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by Man on 31.01.2017.
 */
public class BankClient {
    public static void main(String[] args) {
        String serverIP = "localhost";
        int serverPort = 4444;
        try {
            Socket socket = new Socket(serverIP, serverPort);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.flush();
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line;

            while (true) {
                System.out.println(in.readUTF());
                out.writeUTF(keyboard.readLine());
                out.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

