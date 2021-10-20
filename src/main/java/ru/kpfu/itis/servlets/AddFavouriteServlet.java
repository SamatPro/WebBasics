package ru.kpfu.itis.servlets;

import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/add-favourite")
public class AddFavouriteServlet extends HttpServlet {
    private ProductsService productsService;

    private final String URL = "jdbc:postgresql://localhost:5432/samat_hw";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "databasepass";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Long productId = Long.parseLong(req.getParameter("id"));
        Long userId = productsService.getUserID(cookies);

        if(userId == null) {
            resp.sendRedirect("/signIn");
            return;
        }
        productsService.addToFavourite(userId, productId);
        resp.sendRedirect("/products");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            AuthRepository authRepository = new AuthRepositoryImpl(connection);
            ProductsRepository productsRepository = new ProductsRepositoryImpl(connection);
            productsService = new ProductsServiceImpl(productsRepository, authRepository);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }
}
