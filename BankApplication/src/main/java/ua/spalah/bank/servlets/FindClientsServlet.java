package ua.spalah.bank.servlets;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.type.AccountType;
import ua.spalah.bank.services.ClientService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Man on 04.03.2017.
 */
public class FindClientsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");

        String idParam = req.getParameter("id");

        if (idParam != null) {
            long id = Long.parseLong(idParam);
            Client client = clientService.findClientById(id);
            double balance=0;
            double overdraft=0;
            for (Account account: client.getAccounts()) {
                balance+=account.getBalance();
                if(account.getAccountType()== AccountType.CHECKING){
                    overdraft += ((CheckingAccount)account).getOverdraft();
                }
            }
            req.setAttribute("client", client);
            req.setAttribute("balance", balance);
            req.setAttribute("overdraft",overdraft);
            req.getRequestDispatcher("/WEB-INF/jsp/client.jsp").forward(req, resp);
        } else {
            req.setAttribute("clients", clientService.findAllClients());
            req.getRequestDispatcher("/WEB-INF/jsp/client-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");

        String idParam = req.getParameter("id");
        if (idParam != null) {
            long id = Long.parseLong(idParam);
            clientService.deleteClientById(id);
            req.setAttribute("clients", clientService.findAllClients());
            req.getRequestDispatcher("/WEB-INF/jsp/client-list.jsp").forward(req, resp);
        }
    }
}
