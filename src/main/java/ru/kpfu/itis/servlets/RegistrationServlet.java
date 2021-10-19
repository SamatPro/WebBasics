package ru.kpfu.itis.servlets;

import ru.kpfu.itis.forms.UserForm;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.repositories.UsersRepositoryImpl;
import ru.kpfu.itis.services.UsersService;
import ru.kpfu.itis.services.UsersServicesImpl;

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

@WebServlet("/signUp")
public class RegistrationServlet extends HttpServlet {

    private UsersService usersService;

    private final String URL = "jdbc:postgresql://localhost:5432/samat_hw";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "databasepass";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            UsersRepository usersRepository = new UsersRepositoryImpl(connection);
            usersService = new UsersServicesImpl(usersRepository);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserForm userForm = new UserForm();
        userForm.setLogin(request.getParameter("login"));
        userForm.setPassword(request.getParameter("password"));
        userForm.setFirstName(request.getParameter("firstName"));
        userForm.setLastName(request.getParameter("lastName"));

        usersService.register(userForm);

        response.sendRedirect("/signIn");
    }
}
