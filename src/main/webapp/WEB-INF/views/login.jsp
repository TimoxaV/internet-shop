<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<h4 style="color: red">${message}</h4>
<form method="post" action="">
    Enter your login: <input type="text" name="login"required="required">
    Enter your password: <input type="password" name="pwd" required="required">
    <button type="submit">Login</button>
</form>
<a href="${pageContext.request.contextPath}/registration">Register now</a><br>
</body>
</html>
