<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello World</h1>
<h1>Welcome</h1>
<a href="${pageContext.request.contextPath}/inject-data">inject test data</a><br>
<a href="${pageContext.request.contextPath}/user/all">user/all</a><br>
<a href="${pageContext.request.contextPath}/registration">register</a><br>
<a href="${pageContext.request.contextPath}/product/all">all products</a><br>
<a href="${pageContext.request.contextPath}/cart/products">cart</a><br>
<a href="${pageContext.request.contextPath}/user/orders">user orders</a><br>
<a href="${pageContext.request.contextPath}/orders/all">all orders</a><br>
<a href="${pageContext.request.contextPath}/products/manage">manage products</a><br>
<a href="${pageContext.request.contextPath}/logout">logout</a><br>
</body>
</html>
