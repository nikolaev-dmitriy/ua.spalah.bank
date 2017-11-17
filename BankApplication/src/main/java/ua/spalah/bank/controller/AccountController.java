package ua.spalah.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Man on 23.03.2017.
 */
@Controller
public class AccountController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(path="/client/account/add", method = RequestMethod.GET)
    public String addAccount(Model model,
                             @RequestParam(name="clientId") String clientIdParam)
    {
        Client client;
        if (clientIdParam != null) {
            client = clientService.findClientById(Long.parseLong(clientIdParam));
        } else {
            client = new Client();
        }
        model.addAttribute("clientName", client.getName());
        model.addAttribute("clientId", clientIdParam);
        return "/add-account";

    }

    @RequestMapping(path="/client/account/add", method = RequestMethod.POST)
    public String addAccount(Model model,
                             @RequestParam(name="id", required = false) String idParam,
                             @RequestParam(name="clientId") String clientIdParam,
                             @RequestParam(name="accountType") String typeParam,
                             @RequestParam(name="balance") String balanceParam,
                             @RequestParam(name="overdraft") String overdraftParam
                             )
    {
        Account account = createAccount(typeParam, balanceParam, overdraftParam);
        if (idParam != null && !idParam.equals("0")) {
            account.setId(Long.parseLong(idParam));
            account = accountService.updateAccount(Long.parseLong(clientIdParam), account);
        } else {
            Client client = clientService.findClientById(Long.parseLong(clientIdParam.split(",")[0]))
                    ;
            account = accountService.addAccount(client, account);
        }
        return "redirect:/client/account?id=" + account.getId()+"&clientId="+clientIdParam.split(",")[0];

    }

    private Account createAccount(String typeParam, String balanceParam, String overdraftParam) {
        if (typeParam.equals("CHECKING")) {
            return new CheckingAccount(Double.parseDouble(balanceParam),Double.parseDouble(overdraftParam));
        } else {
            return new SavingAccount(Double.parseDouble(balanceParam));
        }

    }

    @RequestMapping(path="/client/account")
    public String editAccount(Model model,
                              @RequestParam (name="id") String idParam,
                              @RequestParam (name="clientId") String clientIdParam
                              )
    {
        if (idParam!=null) {
            long id = Long.parseLong(idParam);
            Account account = accountService.findAccountById(id);
            long clientId = Long.parseLong(clientIdParam);
            Client client = clientService.findClientById(clientId);
            model.addAttribute("clientName",client.getName());
            model.addAttribute("clientId",clientIdParam);
            model.addAttribute("account",account);
            model.addAttribute("clients",clientService.findAllClients());
            return "/account";
        }
        return "/client";
    }

    @RequestMapping(path="/client/edit/set-active-account")
    public String setActiveAccount(Model model,
                                   @RequestParam(name="id") String idParam,
                                   @RequestParam(name="clientId") String clientIdParam
                                   )
    {
        if (idParam != null) {
            long id=Long.parseLong(idParam);
            long clientId = Long.parseLong(clientIdParam);
            Client client = clientService.findClientById(clientId);
            Account account = accountService.findAccountById(id);
            accountService.setActiveAccount(client, account);
        }
        return "redirect:/client?id="+clientIdParam;
    }

    @RequestMapping(path="/client/account/deposit")
    public String deposit(Model model,
                          @RequestParam(name="id") String idParam,
                          @RequestParam(name="clientId") String clientIdParam,
                          @RequestParam(name="amount") String amountParam
                          )
    {
        if(idParam!=null) {
            long id = Long.parseLong(idParam);
            Account account = accountService.findAccountById(id);
            Double amount = Double.parseDouble(amountParam);
            long clientId=Long.parseLong(clientIdParam);
            accountService.deposit(clientId, account,amount);
            return "redirect:/client/account?id="+account.getId()+"&clientId="+clientId;
        } else {
            return "redirect:/client";
        }
    }

    @RequestMapping(path="/client/account/withdraw")
    public String withdraw(Model model,
                           @RequestParam(name="id") String idParam,
                           @RequestParam(name="clientId") String clientIdParam,
                           @RequestParam(name="amount") String amountParam
                           )
    {
        if(idParam!=null) {
            long id = Long.parseLong(idParam);
            Account account = accountService.findAccountById(id);
            Double amount = Double.parseDouble(amountParam);
            long clientId=Long.parseLong(clientIdParam);
            try {
                accountService.withdraw(clientId, account,amount);
            } catch (NotEnoughFundsException e) {

            }
            return "redirect:/client/account?id="+account.getId()+"&clientId="+clientId;
        } else {
            return "redirect:/client";
        }
    }
}
