package ru.kpfu.itis.servlets;

import org.postgresql.Driver;
import ru.kpfu.itis.forms.UserForm;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.repositories.UsersRepositoryImpl;
import ru.kpfu.itis.services.UsersService;
import ru.kpfu.itis.services.UsersServicesImpl;

import javax.servlet.ServletConfig;
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


    @Override
    public void init(ServletConfig config) {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
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
