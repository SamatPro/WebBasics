<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 13.10.2021
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Продукт</th>
                <th>Описание</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td><c:out value="${product.id}"/> </td>
                    <td><c:out value="${product.title}"/> </td>
                    <td><c:out value="${product.description}"/> </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
