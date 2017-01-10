package listeners;

import models.Client;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Man on 07.01.2017.
 */
public class RegistrationLoggerListener implements ClientRegistrationListener {
    @Override
    public void onClientAdded(Client client) {
        System.out.println(("Client " + client.getName() + " added on " + LocalDate.now() + " " + LocalTime.now()));
    }
}
