<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 28.09.2021
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>

<form action="/signIn" method="post" class="form">
    <p>Sign In</p>
    <p>
        <label for="login">Login<input id="login" type="text" name="login"></label><br>
        <label for="password">Password<input id="password" type="password" name="password"></label><br>

    <p><button type="submit">Вход</button></p><br>

    <a href="https://oauth.vk.com/authorize?client_id=7984753&redirect_uri=http://localhost:8080/vk&display=page&v=5.131&scope=status,email">Войти через ВК</a>
</form>
<h3>${signInStatus}</h3>

</body>
</html>
