package ru.kpfu.itis.servlets;


import ru.kpfu.itis.models.User;
import ru.kpfu.itis.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
            usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("auth")) {
                User user = usersService.findUserByCookieValue(cookie.getValue());
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
                }
            }
        }
        response.sendRedirect("/signIn");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
