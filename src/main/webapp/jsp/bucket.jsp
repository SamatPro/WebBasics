<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 13.10.2021
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>
<h1>Bucket</h1>
<table>
    <tr>
        <th>ID           </th>
        <th>Название     </th>
        <th>Стоимость    </th>
        <th>Описание     </th>
    </tr>
    <c:forEach items="${bucket}" var="product">
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
              <button id="remove${product.id}" onclick="removeProductBucket(${product.id})">Remove</button>
            </td>
        </tr>
    </c:forEach>
</table>

<script>
    function removeProductBucket(productId) {
        $.ajax({
            url: '/bucket',
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
