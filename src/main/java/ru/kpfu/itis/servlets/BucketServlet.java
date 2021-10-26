package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;
import ru.kpfu.itis.services.UsersServicesImpl;

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
import java.util.Arrays;
import java.util.List;

@WebServlet("/bucket")
public class BucketServlet extends HttpServlet {
    //todo получить список продуктов из корзины пользователя и вывести на bucket.jsp

    private ProductsService productsService;
    private AuthRepository authRepository;

    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "zR#BKdWn";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ProductsRepository productsRepository = new ProductsRepositoryImpl(connection);
            authRepository = new AuthRepostoryImpl(connection);
            productsService = new ProductsServiceImpl(productsRepository);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auth auth = authRepository.findByCookieValue(Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals("auth")).findFirst().get().getValue());
        if (auth == null) {
            resp.sendRedirect("/signIn");
        } else {
            List<Product> products = productsService.allInBucketById(auth.getUser().getId());
            req.setAttribute("bucket", products);
            int i = 0;
            req.getRequestDispatcher("/jsp/bucket.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("idToRemove"));
        Auth auth = authRepository.findByCookieValue(Arrays
                .stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("auth"))
                .findFirst()
                .get()
                .getValue());
        if (auth == null) {
            resp.sendRedirect("/signIn");
        } else {
            String isWork = req.getParameter("isWork");
            if (isWork != null) {
                productsService.removeFromBucket(auth.getUser().getId(), productId);
                req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
                return;
            }
            productsService.addingToBucket(auth.getUser().getId(), productId);
            resp.sendRedirect("/bucket");
        }
    }
}
