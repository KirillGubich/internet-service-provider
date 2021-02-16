<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<%--<h1><a href="${pageContext.request.contextPath}/controller?command=login">Index page</a></h1>--%>
<h2>${requestScope.users}</h2>
<c:if test="${not empty requestScope.users}">
    <h2>Columns</h2>
    <ul>
        <c:forEach var="user" items="${requestScope.users}">
            <li>${user}, Hello!</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
