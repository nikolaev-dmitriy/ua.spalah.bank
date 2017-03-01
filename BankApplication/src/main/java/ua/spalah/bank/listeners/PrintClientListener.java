package main.java.ua.spalah.bank.listeners;

import main.java.ua.spalah.bank.models.Client;

/**
 * Created by Man on 07.01.2017.
 */
public class PrintClientListener implements ClientRegistrationListener {
    @Override
    public void onClientAdded(Client client) {
        System.out.println(client.toString());
    }
}
