package ua.spalah.bank.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Man on 07.01.2017.
 */
public class Bank {
    private Map<String,Client> clients = new HashMap<String,Client>();

    public Map<String,Client> getClients() {
        return clients;
    }

}

