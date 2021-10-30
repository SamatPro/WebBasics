package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.*;

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
import java.util.List;

@WebServlet("/bucket")
public class BucketServlet extends HttpServlet {
    //todo получить список продуктов из корзины пользователя и вывести на bucket.jsp
    private ProductsService productsService;
    private UsersService usersService;

    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "K2002080210k";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            ProductsRepository productsRepository = new ProductsRepositoryImpl(connection);
            productsService = new ProductsServiceImpl(productsRepository);

            UsersRepository usersRepository = new UsersRepositoryImpl(connection);
            AuthRepository authRepository = new AuthRepostoryImpl(connection);
            usersService = new UsersServicesImpl(usersRepository, authRepository);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        req.setCharacterEncoding("UTF-8");

        Cookie cookie = CookieService.getCookie(req, "auth");
        if (cookie != null) {
            User user = usersService.findUserByCookieValue(cookie.getValue());
            if (user != null) {
                List<Product> products = productsService.findInBucketByUserId(user.getId());
                req.setAttribute("products", products);
                req.getRequestDispatcher("/jsp/bucket.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("/signIn");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = CookieService.getCookie(req, "auth");
        if (cookie != null) {
            User user = usersService.findUserByCookieValue(cookie.getValue());
            if (user != null) {
                String idToRemove = req.getParameter("idToRemove");
                if (idToRemove != null) {
                    productsService.removeFromBucket(user.getId(), Long.valueOf(idToRemove));
                    req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
                }
            }
        }
    }
}
