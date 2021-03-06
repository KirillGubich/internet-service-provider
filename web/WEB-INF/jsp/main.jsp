<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet internet provider">
    <style>
        <%@include file="/WEB-INF/styles/main.css"%>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/pageFooter.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <title>JoiNet</title>
</head>
<body>
<header>
    <nav class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller">Joi<span class="logoN">N</span>et</a>
        </div>
        <div class="menu">
            <ul class="nav">
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">Tariffs</a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_user_login_page">Sign in</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="mainpage">
    <div class="description">
        <h1>Establish a connection</h1>
        <p> JoiNet is one of the most reliable internet service providers that offers home high speed internet
            connection. We value our reputation and guarantee our clients the quality of our services. Join us! </p>
        <br>
        <a href="${pageContext.request.contextPath}/controller?command=show_user_sign_up_page" class="join">JOIN</a>
    </div>
    <footer class="page_footer">
        <div class="col item social"><a href="#"><i class="fab fa-facebook-f"></i></a><a href="#"><i
                class="fab fa-skype"></i></a><a href="#"><i class="fab fa-github"></i></a><a
                href="#"><i class="fab fa-instagram"></i></a></div>
        <div class="copyright">
            &copy 2021 - Kirill Gubich
        </div>
    </footer>
</div>
</body>
</html>
