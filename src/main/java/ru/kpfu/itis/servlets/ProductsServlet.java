package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;
import ru.kpfu.itis.services.UsersService;

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

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private ProductsService productsService;
    private UsersService usersService;
    private AuthRepository authRepository;

    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            ProductsRepository productsRepository = new ProductsRepositoryImpl(connection);
            productsService = new ProductsServiceImpl(productsRepository);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        List<Product> products = productsService.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
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

            String addToBucketId = req.getParameter("to_bucket");
            if (addToBucketId != null) {
                productsService.addToBucket(auth.getId(), Long.valueOf(addToBucketId));
                req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
            }

            String addToFavouriteId = req.getParameter("to_favourite");
            if (addToFavouriteId != null) {
                productsService.addToFavourites(auth.getId(), Long.valueOf(addToFavouriteId));
                req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
            }
        }
    }
}

