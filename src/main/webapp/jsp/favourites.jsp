<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 13.10.2021
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Favourites</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>
<div id="favourites">
    <table>
        <h1>Favourites</h1>
        <tr>
            <th>ID           </th>
            <th>Название     </th>
            <th>Стоимость    </th>
            <th>Описание     </th>
        </tr>
        <c:forEach items="${favourite}" var="product">
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
                    <button id="remove${product.id}" onclick="removeProductFavourites(${product.id})">Remove</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<script>
    function removeProductFavourites(productId) {
        $.ajax({
            url: '/favourites',
            method: 'post',
            dataType: 'json',
            data: {
                idToRemove: productId,
                isWork: "true"
            },
            success: function (data) {
                alert(data);
            }
        })
        location.reload();
    }
</script>
</body>
</html>
