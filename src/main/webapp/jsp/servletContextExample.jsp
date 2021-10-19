<%@ page import="ru.kpfu.itis.services.UsersService" %>
<%@ page import="ru.kpfu.itis.services.ProductsService" %><%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 18.10.2021
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Контекст сервлета
    </title>
</head>
<body>
    <p>
        <%--Так напрямую обращаться через JSP нельзя, это пример получение данных из контекста--%>
        <%
            ProductsService service = (ProductsService) getServletConfig().getServletContext().getAttribute("productsService");
            out.print(service.findAll().toString());
        %>
    </p>
</body>
</html>
