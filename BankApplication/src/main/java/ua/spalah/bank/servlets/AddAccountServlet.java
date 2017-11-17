package ua.spalah.bank.servlets;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Man on 05.03.2017.
 */
public class AddAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getSession().getServletContext();
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        ClientService clientService = (ClientService) context.getAttribute("clientService");

        Account account = createAccount(request);
        String idParam = request.getParameter("id");
        String clientIdParam = request.getParameter("clientId");

        if (idParam != null && !idParam.equals("0")) {
            account.setId(Long.parseLong(idParam));
            account = accountService.updateAccount(Long.parseLong(clientIdParam), account);
        } else {
            Client client = clientService.findClientById(Long.parseLong(clientIdParam));
            account = accountService.addAccount(client, account);
        }
        response.sendRedirect("/client/account?id=" + account.getId()+"&clientId="+clientIdParam);
    }

    private Account createAccount(HttpServletRequest request) {
        String typeParam = request.getParameter("accountType");
        String balanceParam = request.getParameter("balance");
        String overdraftParam = request.getParameter("overdraft");
        if (typeParam.equals("CHECKING")) {
            Account account = new CheckingAccount(Double.parseDouble(balanceParam),Double.parseDouble(overdraftParam));
            return account;
        } else {
            Account account = new SavingAccount(Double.parseDouble(balanceParam));
            return account;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");

        String clientIdParam = request.getParameter("clientId");

        Client client = null;
        if (clientIdParam != null) {
            client = clientService.findClientById(Long.parseLong(clientIdParam));
        } else {
            client = new Client();
        }
        request.setAttribute("clientName", client.getName());
        request.setAttribute("clientId", clientIdParam);
        request.getRequestDispatcher("/WEB-INF/jsp/add-account.jsp").forward(request, response);
    }
}
