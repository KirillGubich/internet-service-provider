<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet user login page">
    <title>Login</title>
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
                <input type="text" placeholder="Login" name="userLogin" required>
            </label><br>
            <label>
                <input type="password" placeholder="Password" name="userPassword" required>
            </label><br>
            <button type="submit" class="loginbtn">Login</button>
            <div class="signup">
                <p>Don't have an account?
                    <a href="${pageContext.request.contextPath}/controller?command=show_user_sign_up_page">Sign up</a>.
                </p>
            </div>
            <h4 style="color: red">${requestScope.errorMessage}</h4>
        </div>
    </form>
</div>
<footer class="page_footer">
    <div class="col item social"><a href="#"><i class="fab fa-facebook-f"></i></a><a href="#"><i
            class="fab fa-skype"></i></a><a href="#"><i class="fab fa-github"></i></a><a
            href="#"><i class="fab fa-instagram"></i></a></div>
    <div class="copyright">
        &copy 2021 - Kirill Gubich
    </div>
</footer>
</body>
</html>
