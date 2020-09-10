<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<h1>Please provide your login and password</h1>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/registration">
    Enter your name: <input type="text" name="name" required="required">
    Enter your login: <input type="text" name="login"required="required">
    Enter your password: <input type="password" name="pwd" required="required">
    Repeat your password: <input type="password" name="pwd-repeat"required="required">

    <button type="submit">Register</button>
</form>
<a href="${pageContext.request.contextPath}/">main page</a>
</body>
</html>
