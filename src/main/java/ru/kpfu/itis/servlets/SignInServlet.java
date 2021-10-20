package ru.kpfu.itis.servlets;

import ru.kpfu.itis.forms.LoginForm;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.AuthRepostoryImpl;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.repositories.UsersRepositoryImpl;
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

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private UsersService usersService;
    private final String URL = "jdbc:postgresql://localhost:5434/postgres";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "7788";
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            UsersRepository usersRepository = new UsersRepositoryImpl(connection);
            AuthRepository authRepository = new AuthRepostoryImpl(connection);
            usersService = new UsersServicesImpl(usersRepository, authRepository);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/signIn.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        LoginForm loginForm = new LoginForm(login, password);
        Cookie cookie = usersService.signIn(loginForm);

        if (cookie != null) {
            response.addCookie(cookie);
            response.sendRedirect("/profile");
        } else {
            request.setAttribute("signInStatus", "Неправильный логин или пароль");
            request.getRequestDispatcher("jsp/signIn.jsp").forward(request, response);
        }

    }

}
