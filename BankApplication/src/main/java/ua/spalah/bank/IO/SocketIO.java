package ua.spalah.bank.IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Man on 31.01.2017.
 */
public class SocketIO implements IO {
    private DataInputStream in;
    private DataOutputStream out;
    StringBuilder stringBuilder;

    public void initSocketIO(Socket socket) {
        try {
            stringBuilder = new StringBuilder();
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Troubles with socket", e.getCause());
        }
    }

    @Override
    public String read() {
        try {
            out.writeUTF(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            out.flush();
            return in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from socket", e.getCause());
        }
    }

    @Override
    public void write(String s) {

        stringBuilder.append(s);
    }
}
