package ua.spalah.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 23.03.2017.
 */
@Controller
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
}
