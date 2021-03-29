<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="mainPage"/>
<html lang="${language}">
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
                    <a class="locale" href="${pageContext.request.contextPath}/controller?command=set_localization&locale=en">EN</a>
                </li>
                <li>
                    <a class="locale" href="${pageContext.request.contextPath}/controller?command=set_localization&locale=ru">RU</a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=set_localization&locale=es">ES</a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">
                        <fmt:message key="navigation.tariffs"/>
                    </a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_user_login_page">
                        <fmt:message key="navigation.signIn"/>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="mainpage">
    <div class="description">
        <h1><fmt:message key="description.header"/></h1>
        <p><fmt:message key="description.text"/></p>
        <br>
        <a href="${pageContext.request.contextPath}/controller?command=show_user_sign_up_page" class="join">
            <fmt:message key="join.button"/>
        </a>
    </div>
    <footer class="page_footer">
        <div class="col item social">
            <a href="//facebook.com" target="_blank">
                <i class="fab fa-facebook-f"></i>
            </a>
            <a href="//skype.com" target="_blank">
                <i class="fab fa-skype"></i>
            </a>
            <a href="//github.com" target="_blank">
                <i class="fab fa-github"></i>
            </a>
            <a href="//instagram.com" target="_blank">
                <i class="fab fa-instagram"></i>
            </a>
        </div>
        <div class="copyright">
            &copy 2021 - Kirill Gubich
        </div>
    </footer>
</div>
</body>
</html>
