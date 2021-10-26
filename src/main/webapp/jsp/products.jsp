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
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

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
        </tr>
        <td>
        <td>
            <button id="addToBucket${product.id}" onclick="addToBucket(${product.id})">Add to Bucket</button>
        </td>
    </td>
        <td>
            <button id="addToFavourites${product.id}" onclick="addToFavourites(${product.id})">Add to Favourites</button>
        </td>
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

        function sendProduct(){
            let title = document.getElementById('title').value
            let cost = document.getElementById('cost').value
            let description = document.getElementById('description').value

            var product = {
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

        function addToBucket(productId) {
            $.ajax({
                url: '/products',           /* Куда пойдет запрос */
                method: 'post',             /* Метод передачи (post или get) */
                dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
                data: {
                    bucket_adding: productId
                },
                success: bucketSuccess(productId)
            })
        }

        function addToFavourites(productId) {
            $.ajax({
                url: '/products',           /* Куда пойдет запрос */
                method: 'post',             /* Метод передачи (post или get) */
                dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
                data: {
                    favourite_adding: productId
                },
                success: favouriteSuccess(productId)
            })
        }

        function bucketSuccess(index) {
            let id = 'addToBucket' + index
            let b = document.getElementById(id);
            b.style.backgroundColor = "green";
            b.textContent = "In bucket";
            b.disabled = true;
        }

        function favouriteSuccess(index) {
            let id = 'addToFavourites' + index
            let b = document.getElementById(id);
            b.style.backgroundColor = "red";
            b.textContent = "In Favourites";
            b.disabled = true;
        }

    </script>


</body>
</html>
