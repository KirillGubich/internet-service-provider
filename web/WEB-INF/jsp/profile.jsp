<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 17.02.2021
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<h2>Hello, ${sessionScope.userLogin}</h2>
<h3>You are ${sessionScope.userRole}</h3>

<a href="/controller?command=logout">sign out</a>
</body>
</html>
