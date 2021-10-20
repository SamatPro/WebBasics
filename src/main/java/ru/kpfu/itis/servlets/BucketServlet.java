package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.AuthRepositoryImpl;
import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;

import javax.servlet.ServletException;
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
    private AuthRepository authRepository;
    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "sadafa54ga";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            ProductsRepository productsRepository = new ProductsRepositoryImpl(connection);
            productsService = new ProductsServiceImpl(productsRepository);
            authRepository = new AuthRepositoryImpl(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auth auth = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                auth = authRepository.findByCookieValue(cookie.getValue());
            }
        }
        if (auth == null) {
            resp.sendRedirect("/signIn");
        } else {
            List<Product> products = productsService.findAllInBucketById(auth.getUser().getId());
            req.setAttribute("bucket", products);
            req.getRequestDispatcher("/jsp/bucket.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("id"));
        Auth auth = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                auth = authRepository.findByCookieValue(cookie.getValue());
            }
        }
        if (auth == null) {
            resp.sendRedirect("/signIn");
        } else {
            productsService.addToBucket(auth.getUser().getId(), productId);
            resp.sendRedirect("/bucket");
        }
    }

}
