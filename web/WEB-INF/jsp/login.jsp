<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="loginPage"/>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet user login page">
    <title><fmt:message key="page.title"/></title>
    <style>
        <%@include file="/WEB-INF/styles/login.css"%>
        <%@include file="/WEB-INF/styles/pageFooter.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="signin">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <div class="container">
            <h1 class="logo">
                <a href="${pageContext.request.contextPath}/controller">JoiNet</a>
            </h1>
            <input type='hidden' name='command' value='login'/>
            <label>
                <input type="text" placeholder="<fmt:message key="login.placeholder"/>" name="userLogin" required>
            </label><br>
            <label>
                <input type="password" placeholder="<fmt:message key="password.placeholder"/>" name="userPassword" required>
            </label><br>
            <button type="submit" class="loginbtn"><fmt:message key="button.submit.caption"/></button>
            <div class="signup">
                <p><fmt:message key="registration.request"/>
                    <a href="${pageContext.request.contextPath}/controller?command=show_user_sign_up_page"><fmt:message key="registration.button.caption"/></a>.
                </p>
            </div>
            <c:if test="${requestScope.errorMessage eq true}">
                <h4 style="color: red"><fmt:message key="error.message"/></h4>
            </c:if>
        </div>
    </form>
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
</body>
</html>
