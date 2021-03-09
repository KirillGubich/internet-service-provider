<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet user settings">
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/settings.css"%>
        <%@include file="/WEB-INF/styles/profileFooter.css"%>
        <%@include file="/WEB-INF/styles/support.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <title>Settings</title>
</head>
<body>
<header class="pageHeader">
    <nav class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=show_profile">Joi<span class="logoN">N</span>et</a>
        </div>
        <div class="menu">
            <ul class="nav">
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=show_settings_page">Settings</a></li>
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">Tariffs</a></li>
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=logout">Sign out</a></li>
            </ul>
        </div>
    </nav>
</header>
<div class="settings">
    <div class="settings-cards">
        <h2 class="settingsHeader">Personal settings</h2>
        <div class="profileSettings">
            <div class="small-card small-card-info">
                <header class="small-card-header">
                    <h4 class="small-card-title">Account info</h4>
                </header>
                <div class="small-card__body">
                    <p>id: ${requestScope.userInfo.id}</p>
                    <p>
                        status: <span style="color: ${requestScope.statusColor}">Active</span>
                    </p>
                </div>
            </div>
            <div class="small-card small-card-login">
                <header class="small-card-header">
                    <h4 class="small-card-title">Login</h4>
                </header>
                <div class="small-card-body">
                    <p>${requestScope.userInfo.login}</p>
                </div>

                <footer class="small-card-footer">
                    <a href="#" class="small-card-tooltip">Change login</a>
                </footer>
            </div>
            <div class="small-card small-card-password">
                <header class="small-card-header">
                    <h4 class="small-card-title">Password</h4>
                </header>
                <div class="small-card-body">
                    <p>********</p>
                </div>

                <footer class="small-card-footer">
                    <a href="#" class="small-card-tooltip">Change password</a>
                </footer>
            </div>
            <div class="small-card small-card-balance">
                <header class="small-card__header">
                    <h4 class="small-card-title">Balance</h4>
                </header>
                <div class="small-card-body">
                    <p>${requestScope.userInfo.balance} BYN</p>
                </div>
                <footer class="small-card-footer">
                    <a href="#" class="small-card-tooltip">Top up</a>
                </footer>
            </div>
        </div>
    </div>
    <div class="contact-us">
        <h2 class="supportHeader">Contact us</h2>
        <div class="support_form">
            <form>
                <div class="half left cf">
                    <input type="text" id="input-name" placeholder="Name" required>
                    <input type="email" id="input-email" placeholder="Email address" required>
                    <input type="text" id="input-subject" placeholder="Subject" required>
                </div>
                <div class="half right cf">
                    <label for="input-message"></label>
                    <textarea name="message" id="input-message" placeholder="Message" required></textarea>
                </div>
                <input type="submit" value="Send" id="input-submit">
            </form>
        </div>
    </div>
    <br><br><br>
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
