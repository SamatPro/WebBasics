<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 13.10.2021
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Favourites</title>
</head>
<body>

<table>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Стоимость</th>
        <th>Описание</th>
    </tr>
    <c:forEach var="product" items="${favourite}">
    <tr>
        <td>
            <c:out value="${product.id}"/>
        </td>
        <td>
            <c:out value="${product.title}"/>
        </td>
        <td>
            <c:out value="${product.cost}"/>
        </td>
        <td>
            <c:out value="${product.description}"/>
        </td>
    </tr>
    </c:forEach>
</body>
</html>
