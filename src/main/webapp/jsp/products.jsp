<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 12.10.2021
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Products</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

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
                    <button id="favourites_btn_${product.id}" onclick="addToFavourites(${product.id})">Добавить в избранное</button>
                </td>
                <td>
                    <button id="bucket_btn_${product.id}" onclick="addToBucket(${product.id})">Добавить в корзину</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="form">
    <input id="title" type="text" name="title" placeholder="Название продукта"/>
    <input id="cost" type="number" name="cost" placeholder="Стоимость продукта"/>
    <input id="description" type="text" name="description" placeholder="Описание"/>
    <button id="sendProduct" onclick="sendProduct()">Отправить</button>
</div>


<script>
    function isAuthenticated(){
        let docCookies = document.cookie;
        let prefix = "auth=";
        let begin = docCookies.indexOf("; " + prefix);
        if (begin == -1) {
            begin = docCookies.indexOf(prefix);
            if (begin != 0) return false;
        }
        return true;
    }
    function addToFavourites(id){
        let btn = document.getElementById('favourites_btn_'+id);
        let url = '/add-favourite?id=' + id;
        if (!isAuthenticated()){
            btn.style.backgroundColor = 'yellow';
            btn.innerText = 'Авторизуйтесь!';
            return;
        }
        $.post(
            url,
            successBtn(btn)
        );
    }
    function successBtn(btn){
        btn.style.backgroundColor = 'green';
        btn.disabled = 'true';
        btn.innerText = 'Добавлено!';
        return true;
    }
    function addToBucket(id){
        let btn = document.getElementById('bucket_btn_'+id);
        let url = '/add-bucket?id=' + id;
        if (!isAuthenticated()){
            btn.style.backgroundColor = 'yellow';
            btn.innerText = 'Авторизуйтесь!';
            return;
        }
        $.ajax({
            url: url,           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
                id: id /* Параметры передаваемые в запросе. */
            },
            success: successBtn(btn)
        })
    }
    function sendProduct(){
        let title = document.getElementById('title').value
        let cost = document.getElementById('cost').value
        let description = document.getElementById('description').value
        let product = {
            title: title,
            cost: cost,
            description: description
        }
        $.ajax({
            url: '/products',           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
                product: JSON.stringify(product) /* Параметры передаваемые в запросе. */
            },
            success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
                alert(data);            /* В переменной data содержится ответ от /products. */
            }
        })
    }
</script>


</body>
</html>