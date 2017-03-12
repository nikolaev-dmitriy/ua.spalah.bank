package ua.spalah.bank.servlets;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
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
public class EditAccountServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        ServletContext context = req.getSession().getServletContext();
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        String idParam = req.getParameter("id");
        String clientIdParam = req.getParameter("clientId");
        if (idParam!=null) {
            long id = Long.parseLong(idParam);
            Account account = accountService.findAccountById(id);
            long clientId = Long.parseLong(clientIdParam);
            Client client = clientService.findClientById(clientId);
            req.setAttribute("clientName",client.getName());
            req.setAttribute("clientId",clientIdParam);
            req.setAttribute("account",account);
            req.setAttribute("clients",clientService.findAllClients());
            req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req,resp);
        }
    }
}
