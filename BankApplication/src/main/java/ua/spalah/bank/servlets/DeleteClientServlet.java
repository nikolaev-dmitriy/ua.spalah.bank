package ua.spalah.bank.servlets;

import ua.spalah.bank.services.ClientService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Man on 06.03.2017.
 */
public class DeleteClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");

        String idParam = req.getParameter("id");
        if (idParam != null) {
            long id = Long.parseLong(idParam);
            clientService.deleteClientById(id);
            req.setAttribute("clients", clientService.findAllClients());
            req.getRequestDispatcher("/WEB-INF/jsp/client-list.jsp").forward(req, resp);
        }
        resp.sendRedirect("/client");
    }
}
