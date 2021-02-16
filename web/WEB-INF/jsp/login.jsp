<%--
  Created by IntelliJ IDEA.
  User: Mikhail_Niakrasau
  Date: 2/12/2021
  Time: 8:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/controller?command=login">
    <input name="userName">
    <input type="password" name="userPassword">
    <input type="submit">
</form>
</body>
</html>
