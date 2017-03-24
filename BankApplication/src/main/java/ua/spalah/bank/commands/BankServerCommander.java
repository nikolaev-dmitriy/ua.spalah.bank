//package ua.spalah.bank.commands;
//
//import ua.spalah.bank.IO.SocketIO;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.InputMismatchException;
//
///**
// * Created by Man on 31.01.2017.
// */
//public class BankServerCommander extends BankCommander {
//    private int port = 4444;
//    public BankServerCommander() {
//        super(new SocketIO());
//    }
//
//    public void run() {
//        SocketIO socketIO = (SocketIO)io;
//        try {
//            ServerSocket serverSocket = new ServerSocket(port);
//            Socket socket = serverSocket.accept();
//            socketIO.initSocketIO(socket);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while (true) {
//            ArrayList<Integer> canBeSelected = new ArrayList<>(commands.length);
//            io.write("--------------------------MENU---------------------------------\n");
//            if (currentClient == null) {
//                for (int i = 0; i < commands.length; i++) {
//                    if (!commands[i].currentClientIsNeeded()) {
//                        canBeSelected.add(i+1);
//                        socketIO.write(i+1+") " + commands[i].getCommandInfo()+"\n");
//                    }
//                }
//                io.write("Current client is not selected"+"\n");
//            } else {
//                for (int i = 0; i < commands.length; i++) {
//                    canBeSelected.add(i+1);
//                    socketIO.write(i + 1 + ") " + commands[i].getCommandInfo()+"\n");
//                }
//                socketIO.write("Current client: " + currentClient.getName()+"\n");
//            }
//            try {
//                socketIO.write("Enter command number: ");
//                int command = Integer.parseInt(io.read().trim());
//                if (canBeSelected.contains(command)) {
//                    commands[command - 1].execute();
//                } else {
//                    socketIO.write("This command is not available"+"\n");
//                }
//            } catch (ArrayIndexOutOfBoundsException e) {
//                socketIO .write("Wrong command number!"+"\n");
//
//            } catch (InputMismatchException e) {
//                socketIO.write("This is not a number!"+"\n");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        BankServerCommander bankServerCommander = new BankServerCommander();
//        bankServerCommander.run();
//
//    }
//}
