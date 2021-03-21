<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="settingsPage"/>
<html lang="${language}">
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
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#loginLink").click(function (event) {
                event.preventDefault();
                $(".overlay").fadeToggle("fast");
            });

            $(".overlayLink").click(function (event) {
                event.preventDefault();
                var action = $(this).attr('data-action');

                $("#loginTarget").load("ajax/" + action);

                $(".overlay").fadeToggle("fast");
            });

            $(".close").click(function () {
                $(".overlay").fadeToggle("fast");
            });

            $(document).keyup(function (e) {
                if (e.keyCode === 27 && $(".overlay").css("display") !== "none") {
                    event.preventDefault();
                    $(".overlay").fadeToggle("fast");
                }
            });
        });
        $(document).ready(function () {
            $("#loginLink").click(function (event) {
                event.preventDefault();
                $(".overlayPay").fadeToggle("fast");
            });

            $(".overlayPayLink").click(function (event) {
                event.preventDefault();
                var action = $(this).attr('data-action');

                $("#loginTarget").load("ajax/" + action);

                $(".overlayPay").fadeToggle("fast");
            });

            $(".closePay").click(function () {
                $(".overlayPay").fadeToggle("fast");
            });

            $(document).keyup(function (e) {
                if (e.keyCode === 27 && $(".overlayPay").css("display") !== "none") {
                    event.preventDefault();
                    $(".overlayPay").fadeToggle("fast");
                }
            });
        });
    </script>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
<header class="pageHeader">
    <nav class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=show_profile">Joi<span class="logoN">N</span>et</a>
        </div>
        <div class="menu">
            <ul class="nav">
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_settings_page">
                        <fmt:message key="navigation.settings"/>
                    </a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">
                        <fmt:message key="navigation.tariffs"/>
                    </a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=logout">
                        <fmt:message key="navigation.logout"/>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="settings">
    <div class="settings-cards">
        <h2 class="settingsHeader"><fmt:message key="personalSettings.caption"/></h2>
        <div class="profileSettings">
            <div class="small-card small-card-info">
                <header class="small-card-header">
                    <h4 class="small-card-title"><fmt:message key="accountInfo.caption"/></h4>
                </header>
                <div class="small-card__body">
                    <p>id: ${requestScope.userInfo.id}</p>
                    <p>
                        <fmt:message key="status.caption"/>:
                        <c:choose>
                            <c:when test="${requestScope.status eq 'Active'}">
                                <span style="color: green"><fmt:message key="status.active"/></span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: green"><fmt:message key="status.blocked"/></span>
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
            <div class="small-card small-card-login">
                <header class="small-card-header">
                    <h4 class="small-card-title"><fmt:message key="login.caption"/></h4>
                </header>
                <div class="small-card-body">
                    <p>${requestScope.userInfo.login}</p>
                </div>
            </div>
            <div class="small-card small-card-password">
                <header class="small-card-header">
                    <h4 class="small-card-title"><fmt:message key="password.caption"/></h4>
                </header>
                <div class="small-card-body">
                    <p>********</p>
                </div>

                <footer class="small-card-footer">
                    <a href="#" class="overlayLink"><fmt:message key="password.change.caption"/></a>
                </footer>
            </div>
            <div class="small-card small-card-balance">
                <header class="small-card__header">
                    <h4 class="small-card-title"><fmt:message key="balance.caption"/></h4>
                </header>
                <div class="small-card-body">
                    <c:choose>
                        <c:when test="${requestScope.userInfo.balance < 0}">
                            <p style="color: red">${requestScope.userInfo.balance}</p>
                        </c:when>
                        <c:otherwise>
                            <p>${requestScope.userInfo.balance}</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <footer class="small-card-footer">
                    <a href="#" class="overlayPayLink"><fmt:message key="topUp.caption"/></a>
                </footer>
            </div>
        </div>
        <div class="overlay" style="display: none;">
            <div class="login-wrapper">
                <div class="login-content" id="loginTarget">
                    <a class="close">x</a>
                    <h3><fmt:message key="changePassword.header"/></h3>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type='hidden' name='command' value='change_password'/>
                        <label>
                            <input type="password" name="password" placeholder="<fmt:message key="currentPassword.placeholder"/>" required/>
                        </label>
                        <label>
                            <input type="password" name="newPassword" placeholder="<fmt:message key="newPassword.placeholder"/>"
                                   pattern="(.){8,200}" required/>
                        </label>
                        <label>
                            <input type="password" name="repNewPassword" placeholder="<fmt:message key="newPasswordRep.placeholder"/>"
                                   pattern="(.){8,200}" required/>
                        </label>
                        <p>
                            <fmt:message key="about.password.first"/><br>
                            <fmt:message key="about.password.second"/>
                        </p>
                        <button type="submit"><fmt:message key="changePassword.submit"/></button>
                    </form>
                </div>
            </div>
        </div>
        <div class="overlayPay" style="display: none;">
            <div class="login-wrapper">
                <div class="login-content">
                    <a class="closePay">x</a>
                    <h3><fmt:message key="topUp.header"/></h3>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type='hidden' name='command' value='top_up_balance'/>
                        <label>
                            <input type="number" min="1" step="any" name="topUpValue" placeholder="<fmt:message key="topUp.placeholder"/>" required/>
                        </label>
                        <button type="submit"><fmt:message key="topUp.submit"/></button>
                    </form>
                </div>
            </div>
        </div>
        <c:choose>
            <c:when test="${not empty requestScope.error eq true}">
                <h3><fmt:message key="error.message"/></h3>
            </c:when>
            <c:otherwise>
                <br><br><br>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="contact-us">
        <h2 class="supportHeader"><fmt:message key="contactUs.caption"/></h2>
        <div class="support_form">
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type='hidden' name='command' value='contact_support'/>
                <div class="half left cf">
                    <input type="text" id="input-name" name="name" placeholder="<fmt:message key="name.placeholder"/>" required>
                    <input type="email" id="input-email" name="email" placeholder="Email" required>
                    <input type="text" id="input-subject" name="subject" placeholder="<fmt:message key="subject.placeholder"/>" required>
                </div>
                <div class="half right cf">
                    <label for="input-message"></label>
                    <textarea name="message" id="input-message" placeholder="<fmt:message key="message.caption"/>" required></textarea>
                </div>
                <button type="submit" id="input-submit"> <fmt:message key="send.button.caption"/></button>
            </form>
        </div>
    </div>
    <br><br><br>
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
