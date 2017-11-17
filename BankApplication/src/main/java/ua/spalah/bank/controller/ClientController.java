package ua.spalah.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import java.util.List;

/**
 * Created by Man on 23.03.2017.
 */
@Controller
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/client")
    public String getClient(@RequestParam(name = "id", required = false) String idParam,
                            Model model) {
        long id = Long.parseLong(idParam);
        Client client = clientService.findClientById(id);
        double balance = clientService.getTotalBalance(client);
        double overdraft = clientService.getTotalOverdraft(client);
        model.addAttribute("client", client);
        model.addAttribute("balance", balance);
        model.addAttribute("overdraft", overdraft);
        List<Account> accounts = clientService.getClientAccounts(client);
        model.addAttribute("accounts", accounts);
        model.addAttribute("activeAccount", clientService.findClientActiveAccount(client));
        return "/client";
    }

    @RequestMapping(value = "/client-list")
    public String getClientList(Model model) {
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        return "/client-list";
    }

    @RequestMapping(value = "/client/delete")
    public String deleteClient(Model model,
                               @RequestParam(name = "id") String idParam) {
        if (idParam != null) {
            long id = Long.parseLong(idParam);
            clientService.deleteClientById(id);
        }
        model.addAttribute("clients", clientService.findAllClients());
        return "/client-list";
    }

    @RequestMapping(path="/client/edit", method = RequestMethod.GET)
    public String editClient(Model model,
        @RequestParam (name="id",required = false) String idParam)
    {
        Client client = null;
        if (idParam != null) {
            client = clientService.findClientById(Long.parseLong(idParam));

            try {
                model.addAttribute("activeAccountId", clientService.findClientActiveAccount(client).getId());
            } catch (NullPointerException e) {
                model.addAttribute("activeAccountId", 0);
            }
            model.addAttribute("accounts", clientService.getClientAccounts(client));
        } else {
            client = new Client();
        }
        model.addAttribute("client", client);
        return "/edit-client";
    }

    @RequestMapping(path="/client/edit", method = RequestMethod.POST)
    public String editClient(Model model,
                             @RequestParam (name="id",required = false) String idParam,
                            @RequestParam (name="name") String nameParam,
                            @RequestParam (name="gender") String genderParam,
                            @RequestParam (name="city") String cityParam,
                            @RequestParam (name="email") String emailParam,
                            @RequestParam (name="telephone") String phoneParam,
                            @RequestParam (name="activeAccountId") String activeAccountId
                            )
    {
        Client client = createClient(nameParam, genderParam, cityParam, emailParam, phoneParam);
        if (client !=null) {

            if (idParam != null && !idParam.equals("0")) {
                client.setId(Long.parseLong(idParam));
                Account activeAccount = accountService.findAccountById(Long.parseLong(activeAccountId));
                try {
                    accountService.setActiveAccount(client, activeAccount);
                    accountService.updateAccount(client.getId(),activeAccount);
                } catch (NullPointerException | IllegalArgumentException e){
                clientService.updateClient(client);
                }
                return "redirect:/client?id="+client.getId();

            } else {
                client = clientService.saveClient(client);
                return "redirect:/client?id=" + client.getId();
            }
        } else {
            return "redirect:/client-list";
        }
    }

    private Client createClient(String nameParam, String genderParam, String cityParam, String emailParam, String phoneParam) {
        try {
            Client client = new Client(nameParam, Gender.valueOf(genderParam), emailParam, phoneParam, cityParam);
            return client;
        } catch (NullPointerException e) {
            return null;
        }
    }
}