package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.AuthRepostoryImpl;
import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.services.BucketService;
import ru.kpfu.itis.services.BucketServiceImpl;

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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet("/bucket")
public class BucketServlet extends HttpServlet {
    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";
    private Connection connection;
    private BucketService bucketService;
    private AuthRepository authRepository;
    private ProductsRepository productsRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bucketService = new BucketServiceImpl(connection);
        authRepository = new AuthRepostoryImpl(connection);
        productsRepository = new ProductsRepositoryImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> auth = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("auth"))
                .findFirst();
        User user = null;
        if (auth.isPresent()){
            user = authRepository.findByCookieValue(auth.get().getValue()).getUser();
        }
        List<Product> productsInBucketByUserId = null;
        if (user != null) {
            productsInBucketByUserId = productsRepository.findProductsInBucketByUserId(user.getId());
        }
        request.setAttribute("products", productsInBucketByUserId);
        request.getRequestDispatcher("jsp/bucket.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Cookie[] cookies = request.getCookies();
        bucketService.saveToDB(cookies, Long.parseLong(request.getParameter("product_id")));
        response.sendRedirect("/products");
    }
}
