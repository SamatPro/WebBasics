package ru.kpfu.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.forms.ProductForm;
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

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private ProductsService productsService;
    private UsersService usersService;

    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "12345678";

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        List<Product> products = productsService.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Cookie cookie = CookieService.getCookie(req, "auth");
        if (cookie != null) {
            User user = usersService.findUserByCookieValue(cookie.getValue());
            if (user != null) {
                String addToBucketId = req.getParameter("to_bucket");
                if (addToBucketId != null) {
                    productsService.addToBucket(user.getId(), Long.valueOf(addToBucketId));
                    req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
                }

                String addToFavouriteId = req.getParameter("to_favourite");
                if (addToFavouriteId != null) {
                    productsService.addToFavourites(user.getId(), Long.valueOf(addToFavouriteId));
                    req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ProductForm productForm = objectMapper.readValue(req.getParameter("product"), ProductForm.class);
        productsService.add(productForm);

        req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
    }

}
