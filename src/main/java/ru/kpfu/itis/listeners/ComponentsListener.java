package ru.kpfu.itis.listeners;

import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.repositories.UsersRepositoryImpl;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;
import ru.kpfu.itis.services.UsersService;
import ru.kpfu.itis.services.UsersServicesImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@WebListener
public class ComponentsListener implements ServletContextListener {

    private final String URL = "jdbc:postgresql://localhost:5435/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            UsersRepository usersRepository = new UsersRepositoryImpl(connection);
            UsersService usersService = new UsersServicesImpl(usersRepository);
            servletContextEvent.getServletContext().setAttribute("usersService", usersService);

            ProductsRepository productsRepository = new ProductsRepositoryImpl(connection);
            ProductsService productsService = new ProductsServiceImpl(productsRepository);
            servletContextEvent.getServletContext().setAttribute("productsService", productsService);
        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
