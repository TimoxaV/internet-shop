<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
</head>
<body>
<h1>Add products</h1>
<form method="post" action="${pageContext.request.contextPath}/product/add">
    Enter product's name: <input type="text" name="name" required="required">
    Enter product's price (numbers only): <input type="number" name="price" required="required">
    <button type="submit">Add</button>
</form>
<a href="${pageContext.request.contextPath}/">main page</a>
</body>
</html>
