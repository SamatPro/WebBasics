package ru.kpfu.itis.listeners;

import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;
import ru.kpfu.itis.services.UsersServicesImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String URL = "jdbc:postgresql://localhost:5432/itis";
        String USERNAME = "postgres";
        String PASSWORD = "CsM9sVk";
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new IllegalArgumentException("Сайт недоступен!!!");
        }
        ProductsRepository productsRepository = new ProductsRepositoryImpl(connection);
        ProductsService productsService = new ProductsServiceImpl(productsRepository);
        UsersRepository usersRepository = new UsersRepositoryImpl(connection);
        AuthRepository authRepository = new AuthRepositoryImpl(connection);
        UsersServicesImpl usersService = new UsersServicesImpl(usersRepository, authRepository);
        servletContext.setAttribute("productsService",productsService);
        servletContext.setAttribute("usersService",usersService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
