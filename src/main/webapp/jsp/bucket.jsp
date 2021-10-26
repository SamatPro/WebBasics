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
</head>
<body>
<h1>Busket</h1>
<table>
<tr>
    <th>ID           </th>
    <th>Название     </th>
    <th>Стоимость    </th>
    <th>Описание     </th>
</tr>
<c:forEach items="${busket}" var="product">
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
            <button id="remove${product.id}" onclick="remove(${product.id})">Remove</button>
        </td>
    </tr>
</c:forEach>
</table>

<script>
    function remove(id) {
        $.ajax({
            url: '/remove-bucket',           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
                idToRemove: id
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
