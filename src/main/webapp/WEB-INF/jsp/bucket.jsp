<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>

<div>
    <a href="/">На главную</a>
    <a href="/products">Продукты</a>

</div>
<h2>Your bucket</h2>
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
</table>
</div>
 </body>
</html>
