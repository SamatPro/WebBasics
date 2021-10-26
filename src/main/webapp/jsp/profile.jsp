<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 13.10.2021
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>

<p>${user.firstName}</p>
<p>${user.lastName}</p>

<a href="${pageContext.request.contextPath}/products">Продукты</a>
<a href="${pageContext.request.contextPath}/bucket">Корзина</a>
<a href="${pageContext.request.contextPath}/favourites">Избранное</a>


</body>
</html>
