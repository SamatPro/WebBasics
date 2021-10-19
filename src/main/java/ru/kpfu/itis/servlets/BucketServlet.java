package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.AuthRepositoryImpl;
import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
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
    private ProductsService productsService;

    private final String URL = "jdbc:postgresql://localhost:5432/samat_hw";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "databasepass";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long userId = productsService.getUserID(req.getCookies());
        List<Product> products = productsService.findBucket(userId);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/jsp/favourites.jsp").forward(req, resp);

    }

    @Override
    public void init() throws ServletException {
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
