<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Products</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>

<div>
    <a href="/favourites">Избранные</a>
    <a href="/bucket">Корзина</a>
</div>

<div id="form">
    <input id="title" type="text" name="title" placeholder="Название продукта"/>
    <input id="cost" type="number" name="cost" placeholder="Стоимость продукта"/>
    <input id="description" type="text" name="description" placeholder="Описание"/>
    <button id="sendProduct" onclick="sendProduct()">Отправить</button>
</div>

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
                    <form action="/favourites" method="post">
                        <input type="hidden" value="${product.id}" name="id">
                        <button type="submit">Добавить в избранное</button>
                    </form>
                </td>
                <td>
                    <form action="/bucket" method="post">
                        <input type="hidden" value="${product.id}" name="id">
                        <button type="submit">Добавить в корзину</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

</div>

<script>
    function sendProduct() {
        let title = document.getElementById('title').value
        let cost = document.getElementById('cost').value
        let description = document.getElementById('description').value

        const product = {
            title: title,
            cost: cost,
            description: description
        };

        $.ajax({
            url: '/products',           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
                product: JSON.stringify(product) /* Параметры передаваемые в запросе. */
            },
            success: function (data) {   /* функция которая будет выполнена после успешного запроса.  */
                alert(data);            /* В переменной data содержится ответ от /products. */
            }
        })
    }
</script>

</body>
</html>
