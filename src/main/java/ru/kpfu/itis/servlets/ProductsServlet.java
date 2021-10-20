package ru.kpfu.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.Driver;
import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.ProductsRepository;
import ru.kpfu.itis.repositories.ProductsRepositoryImpl;
import ru.kpfu.itis.services.ProductsService;
import ru.kpfu.itis.services.ProductsServiceImpl;

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
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    ProductsService productsService;

    @Override
    public void init(ServletConfig config) {
        productsService = (ProductsService) config.getServletContext().getAttribute("productsService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        List<Product> products = productsService.findAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/jsp/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        ProductForm productForm = objectMapper.readValue(request.getParameter("product"), ProductForm.class);

        productsService.add(productForm);
    }

}
