<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Orders</title>
</head>
<body>
<h1>User Orders</h1>
<table border="1">
    <tr>
        <th>Order ID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/details?id=${order.id}">Order details</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">main page</a>
</body>
</html>
