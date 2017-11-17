package ua.spalah.bank.servlets;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.AccountService;
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
public class EditClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        String idParam = req.getParameter("id");

        Client client = null;
        if (idParam != null) {
            client = clientService.findClientById(Long.parseLong(idParam));

            try {
                req.setAttribute("activeAccountId", clientService.findClientActiveAccount(client).getId());
            } catch (NullPointerException e) {
                req.setAttribute("activeAccountId", 0);
            }
            req.setAttribute("accounts", clientService.getClientAccounts(client));
        } else {
            client = new Client();
        }
        req.setAttribute("client", client);
        req.getRequestDispatcher("/WEB-INF/jsp/edit-client.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        Client client = createClient(req,clientService, accountService);
        String idParam = req.getParameter("id");
        if (client !=null) {

            if (idParam != null && !idParam.equals("0")) {
                client.setId(Long.parseLong(idParam));
                Account activeAccount = accountService.findAccountById(Long.parseLong(req.getParameter("activeAccountId")));
                try {
                    accountService.setActiveAccount(client, activeAccount);
                } catch (IllegalArgumentException e){
                }
                accountService.updateAccount(client.getId(),activeAccount);
                resp.sendRedirect("/client?id="+client.getId());

            } else {
                client = clientService.saveClient(client);
                resp.sendRedirect("/client?id=" + client.getId());
            }
        } else {
            resp.sendRedirect("/client");
        }
//        req.setAttribute("client", client);
//        req.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(req, resp);

    }

    private Client createClient(HttpServletRequest req, ClientService clientService, AccountService accountService) {
        try {
            String nameParam = req.getParameter("name");
            String genderParam = req.getParameter("gender");
            String cityParam = req.getParameter("city");
            String emailParam = req.getParameter("email");
            String phoneParam = req.getParameter("telephone");

            Client client = new Client(nameParam, Gender.valueOf(genderParam), emailParam, phoneParam, cityParam);
            return client;
        } catch (NullPointerException e) {
            return null;
        }

    }
}
