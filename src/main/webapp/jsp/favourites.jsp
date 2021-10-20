<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

</head>
<body>
<table>
    <tr>
        <th>ID           </th>
        <th>Название     </th>
        <th>Стоимость    </th>
        <th>Описание     </th>
        <th>Удалить     </th>
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
                <button id="delete_btn_${product.id}" onclick="deleteItem(${product.id})">Удалить</button>
            </td>
        </tr>
    </c:forEach>
</table>
<script>
    function deleteItem(id) {
        var btn = document.getElementById('delete_btn_'+id);
        var url = '/favourites?id='+id;
        var rowId = btn.closest('tr').rowIndex;

        $.post(
            url,
            deleteRow(rowId)
        )
    }

    function deleteRow(rowId) {
        var table = document.getElementsByTagName('table')[0];
        table.deleteRow(rowId);
        return true;
    }
</script>
</body>
</html>
