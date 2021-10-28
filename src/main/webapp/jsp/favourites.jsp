<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 13.10.2021
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <c:forEach var="product" items="${favourite}">
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
                <button id="deleteProduct${product.id}" onclick="deleteProduct(${product.id})">Удалить</button>
            </td>
        </tr>
    </c:forEach>
</table>
<script>
    function deleteProduct(productId){
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


