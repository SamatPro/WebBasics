<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 13.10.2021
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Favourites</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}/products">
    <button type="submit">Все товары</button>
</form>
<form action="${pageContext.request.contextPath}/bucket">
    <button type="submit">Корзина</button>
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
            <td>
                <button id="remove${product.id}" onclick="removeFromFavourites(${product.id})">Убрать</button>
            </td>
        </tr>
        </c:forEach>
</div>
<script>
    function removeFromFavourites(productId) {
        $.ajax({
            url: '/favourites',           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
                idToRemove: productId
            },
            success: function (data) {   /* функция которая будет выполнена после успешного запроса.  */
                alert(data);            /* В переменной data содержится ответ от /products. */
            }
        })
        location.reload();
    }
</script>

</body>
</html>
