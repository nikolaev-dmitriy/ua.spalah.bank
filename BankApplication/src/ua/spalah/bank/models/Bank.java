package ua.spalah.bank.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Man on 07.01.2017.
 */
public class Bank {
    private List<Client> clients = new ArrayList<>();
    ;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}

