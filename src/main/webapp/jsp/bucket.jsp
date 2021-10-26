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

<div id="products">
    <table>
        <tr>
            <th>ID           </th>
            <th>Название     </th>
            <th>Стоимость    </th>
            <th>Описание     </th>
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
                <button id="remove${product.id}" onclick="removeFromBucket(${product.id})">Удалить</button>
            </td>
        </tr>
        </c:forEach>

</div>

<script>
    function removeFromBucket(productId) {
        $.ajax({
            url: '/bucket',
            method: 'post',
            dataType: 'json',
            data: {
                idToRemove: productId
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
