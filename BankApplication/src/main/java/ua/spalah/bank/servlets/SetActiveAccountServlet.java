package ua.spalah.bank.servlets;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Man on 09.03.2017.
 */
public class SetActiveAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        ClientService clientService=(ClientService) context.getAttribute("clientService");
        String idParam = req.getParameter("id");
        if (idParam != null) {
            long id=Long.parseLong(idParam);
            long clientId = Long.parseLong(req.getParameter("clientId"));
            Client client = clientService.findClientById(clientId);
            accountService.setActiveAccount(client, accountService.findAccountById(id));
        }
        resp.sendRedirect("/client/edit?id="+req.getParameter("clientId"));
    }
}
