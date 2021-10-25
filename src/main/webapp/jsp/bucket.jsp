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
</head>
<body>
<form action="${pageContext.request.contextPath}/products">
    <button type="submit">Все товары</button>
</form>
<form action="${pageContext.request.contextPath}/favourites">
    <button type="submit">Избранное</button>
</form>

<div id="products">
    <table>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Стоимость</th>
            <th>Описание</th>
        </tr>
        <c:forEach var="product" items="${products}">
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

</div>

</body>
</html>
