<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tariffs</title>
</head>
<body>
<c:if test="${not empty requestScope.tariffs}">
    <h2>Tariffs</h2>
    <ul>
        <c:forEach var="tariff" items="${requestScope.tariffs}">
            <li>${tariff.name}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
