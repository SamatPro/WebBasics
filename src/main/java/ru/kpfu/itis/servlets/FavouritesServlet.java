package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.AuthRepostoryImpl;
import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.services.ProductsService;
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

@WebServlet("/favourites")
public class FavouritesServlet extends HttpServlet {

    private AuthRepository authRepository;
    private ProductsService productsService;
    private UsersService usersService;
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
            req.getRequestDispatcher("/jsp/favourites.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookies[] = req.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("auth")) {
                User user = usersService.findUserByCookieValue(cookie.getValue());
                if (user != null) {
                    String idToRemove = req.getParameter("idToRemove");
                    if (idToRemove != null) {
                        productsService.removeFromFavourites(user.getId(), Long.valueOf(idToRemove));
                        req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
                    }
                    req.getRequestDispatcher("jsp/products.jsp").forward(req, resp);
                }
            }
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            String PASSWORD = "postgres";
            String USERNAME = "postgres";
            String URL = "jdbc:postgresql://localhost:5432/test_project";
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            authRepository = new AuthRepostoryImpl(connection);
            productsRepository = new ProductsRepositoryImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }
}
