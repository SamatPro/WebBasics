package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.UsersService;
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
import java.util.List;
import java.util.Optional;

@WebServlet("/favourites")
public class FavouritesServlet extends HttpServlet {
    //todo получить список избранных продуктов пользователя и вывести на favourite.jsp

    private final String URL = "jdbc:postgresql://localhost:5432/test_project";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";
    private AuthRepository authRepository;
    private ProductsRepository productsRepository;

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
            List<Product> products = productsRepository.findFavouriteProductsByUserId(auth.getUser().getId());

            req.setAttribute("favourite", products);
            int i = 0;
            req.getRequestDispatcher("/jsp/favourites.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            Long id = Long.parseLong(req.getParameter("id"));
            productsRepository.addProductToFavourite(auth.getUser().getId(), id);
        }
        resp.sendRedirect("/favourites");
    }

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            authRepository = new AuthRepostoryImpl(connection);
            productsRepository = new ProductsRepositoryImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }
}
