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
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=show_settings_page">Settings</a>
                </li>
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">Tariffs</a>
                </li>
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=logout">Sign out</a>
                </li>
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
                        status: <span style="color: ${requestScope.statusColor}">${requestScope.status}</span>
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
            </div>
            <div class="small-card small-card-password">
                <header class="small-card-header">
                    <h4 class="small-card-title">Password</h4>
                </header>
                <div class="small-card-body">
                    <p>********</p>
                </div>

                <footer class="small-card-footer">
                    <a href="#" class="overlayLink">Change password</a>
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
                    <a href="#" class="overlayPayLink">Top up</a>
                </footer>
            </div>
        </div>
        <div class="overlay" style="display: none;">
            <div class="login-wrapper">
                <div class="login-content" id="loginTarget">
                    <a class="close">x</a>
                    <h3>Change password</h3>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type='hidden' name='command' value='change_password'/>
                        <label>
                            <input type="password" name="password" placeholder="current password" required/>
                        </label>
                        <label>
                            <input type="password" name="newPassword" placeholder="new password"
                                   required/>
                        </label>
                        <label>
                            <input type="password" name="repNewPassword" placeholder="repeat new password"
                                   required/>
                        </label>
                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="overlayPay" style="display: none;">
            <div class="login-wrapper">
                <div class="login-content">
                    <a class="closePay">x</a>
                    <h3>Top up balance</h3>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type='hidden' name='command' value='top_up_balance'/>
                        <label>
                            <input type="number" min="1" step="any" name="topUpValue" placeholder="value" required/>
                        </label>
                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>
        </div>
        <c:choose>
            <c:when test="${not empty requestScope.errorMessage}">
                <h3>${requestScope.errorMessage}</h3>
            </c:when>
            <c:otherwise>
                <br><br><br>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="contact-us">
        <h2 class="supportHeader">Contact us</h2>
        <div class="support_form">
            <form action="${pageContext.request.contextPath}/controller">
                <input type='hidden' name='command' value='contact_support'/>
                <div class="half left cf">
                    <input type="text" id="input-name" name="name" placeholder="Name" required>
                    <input type="email" id="input-email" name="email" placeholder="Email address" required>
                    <input type="text" id="input-subject" name="subject" placeholder="Subject" required>
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
