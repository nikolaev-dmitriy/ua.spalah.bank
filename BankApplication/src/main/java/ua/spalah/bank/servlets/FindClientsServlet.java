package ua.spalah.bank.servlets;

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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        ServletContext context = request.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        String idParam = request.getParameter("id");
        if (idParam == null) {
            request.setAttribute("clients",clientService.findAllClients());
            request.getRequestDispatcher("/WEB-INF/jsp/client-list.jsp").forward(request, response);
        }
    }
}
