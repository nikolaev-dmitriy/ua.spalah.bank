package models;

import java.util.ArrayList;

/**
 * Created by Man on 07.01.2017.
 */
public class Bank {
    private ArrayList<Client> clients;
    public Bank(){
        clients=new ArrayList<>();
    }
    public ArrayList<Client> getClients() {
        return clients;
    }
}

