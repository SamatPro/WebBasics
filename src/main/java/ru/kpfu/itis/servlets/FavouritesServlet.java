package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.AuthRepostoryImpl;
import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;

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

@WebServlet("/favourites")
public class FavouritesServlet extends HttpServlet {
    //todo получить список избранных продуктов пользователя и вывести на favourite.jsp

    private ProductsRepository productsRepository;
    private AuthRepository authRepository;

    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "zR#BKdWn";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            productsRepository = new ProductsRepositoryImpl(connection);
            authRepository = new AuthRepostoryImpl(connection);
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
            List<Product> products = productsRepository.findFavouriteProductsByUserId(auth.getUser().getId());
            req.setAttribute("favourite", products);
            req.getRequestDispatcher("/jsp/favourites.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auth auth = authRepository.findByCookieValue(Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals("auth")).findFirst().get().getValue());
        if (auth == null) {
            resp.sendRedirect("/signIn");
        } else {
            Long id = Long.parseLong(req.getParameter("id"));
            productsRepository.addProductToFavourite(auth.getUser().getId(), id);
        }
        resp.sendRedirect("/favourites");
    }
}
