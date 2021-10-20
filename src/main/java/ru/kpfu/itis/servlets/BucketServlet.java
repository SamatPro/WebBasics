package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.utils.CookieHandler.findCookieByName;

@WebServlet("/bucket")
public class BucketServlet extends HttpServlet {
    //todo получить список продуктов из корзины пользователя и вывести на bucket.jsp

    private ProductsService productsService;
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        productsService = (ProductsService) context.getAttribute("productsService");
        usersService = (UsersService) context.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie auth = findCookieByName(request.getCookies(),"auth").get();
        User user = usersService.findUserByCookieValue(auth.getValue());
        List<Product> products = new ArrayList<>(productsService.findBucketByUserId(user.getId()));

        request.setAttribute("products",products);
        request.getRequestDispatcher("/jsp/bucket.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.parseLong(request.getParameter("id"));
        User user = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")){
                user = usersService.findUserByCookieValue(cookie.getValue());
                break;
            }
        }

        if (user != null) {
            productsService.addProductToBucket(user.getId(), productId);
        }

    }
}
