package ua.spalah.bank.servlets;

import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.AccountService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Man on 06.03.2017.
 */
public class DepositServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getSession().getServletContext();
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        String idParam = request.getParameter("id");
        if(idParam!=null) {
            long id = Long.parseLong(idParam);
            Account account = accountService.findAccountById(id);
            Double amount = Double.parseDouble(request.getParameter("amount"));
            long clientId=Long.parseLong(request.getParameter("clientId"));
            accountService.deposit(clientId, account,amount);
            response.sendRedirect("/client/account?id="+account.getId()+"&clientId="+clientId);
        } else {
            response.sendRedirect("/client");
        }
    }
}
