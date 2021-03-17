<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet admin page">
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
        <%@include file="/WEB-INF/styles/adminMain.css"%>
        <%@include file="/WEB-INF/styles/pageFooter.css"%>
    </style>
    <title>Admin</title>
</head>
<body>
<header>
    <div class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=show_admin_page">
                Joi<span class="logoN">N</span>et
            </a>
        </div>
        <nav class="menu">
            <ul class="nav">
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">Tariffs</a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=logout">Sign out</a>
                </li>
            </ul>
        </nav>
    </div>
</header>
<div class="mainpage">
    <div class="serviceButtons">
        <a href="${pageContext.request.contextPath}/controller?command=show_tariff_settings_page" class="service_btn">Tariffs</a>
        <a href="${pageContext.request.contextPath}/controller?command=show_users_for_admin_page" class="service_btn">Users</a>
        <a href="${pageContext.request.contextPath}/controller?command=show_subscription_settings_page" class="service_btn">Subscriptions</a>
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
