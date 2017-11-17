//package ua.spalah.bank.servlets;
//
//import ua.spalah.bank.dao.AccountDao;
//import ua.spalah.bank.dao.ClientDao;
//import ua.spalah.bank.dao.impl.HibernateAccountDaoImpl;
//import ua.spalah.bank.dao.impl.HibernateClientDaoImpl;
//import ua.spalah.bank.services.AccountService;
//import ua.spalah.bank.services.BankReportService;
//import ua.spalah.bank.services.ClientService;
//import ua.spalah.bank.services.impl.AccountServiceImpl;
//import ua.spalah.bank.services.impl.BankReportServiceImpl;
//import ua.spalah.bank.services.impl.ClientServiceImpl;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
///**
// * Created by Man on 03.03.2017.
// */
//public class ServletContextInitializer implements ServletContextListener {
//    private EntityManagerFactory entityManagerFactory;
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        try {
//////            Class.forName("org.h2.Driver");
//////            String url = "jdbc:h2:tcp://localhost/D:/BankApplication/bank";
//////            Connection connection = DriverManager.getConnection(url, "Dmitriy", "");
//////            connection.setAutoCommit(false);
//////            ClientDao clientDao = new ClientDaoImpl(connection);
////            AccountDao accountDao = new AccountDaoImpl(connection);
//            entityManagerFactory = Persistence.createEntityManagerFactory("BankApplication");
//            ClientDao clientDao = new HibernateClientDaoImpl(entityManagerFactory);
//            AccountDao accountDao = new HibernateAccountDaoImpl(entityManagerFactory);
//            ClientService clientService = new ClientServiceImpl(clientDao, accountDao);
//            AccountService accountService = new AccountServiceImpl(clientDao, accountDao);
//            BankReportService bankReportService = new BankReportServiceImpl(clientDao, accountDao);
//            ServletContext context = servletContextEvent.getServletContext();
//            context.setAttribute("clientService", clientService);
//            context.setAttribute("accountService", accountService);
//            context.setAttribute("bankReportService", bankReportService);
//        } catch (Exception e) {
////            throw new RuntimeException("Initialization error", e);
//            RuntimeException ex = new RuntimeException("Initialization error");
//            ex.initCause(e);
//            throw ex;
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        entityManagerFactory.close();
//    }
//}
