<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet user sign up page">
    <style>
        <%@include file="/WEB-INF/styles/signup.css"%>
        <%@include file="/WEB-INF/styles/pageFooter.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <title>Registration</title></head>
<body>
<div class="signup">
    <form action="${pageContext.request.contextPath}/controller">
        <div class="container">
            <h1 class="logo">
                <a href="${pageContext.request.contextPath}/controller">JoiNet</a>
            </h1>
            <input type='hidden' name='command' value='sign_up'/>
            <label>
                <input type="text" placeholder="Enter login" name="userLogin" required>
            </label><br>
            <label>
                <input type="password" placeholder="Enter Password" name="userPassword" required>
            </label><br>
            <label>
                <input type="password" placeholder="Repeat Password" name="userRepPassword" required>
            </label><br>
            <button type="submit" class="registerbtn">Create</button>
            <div class="signin">
                <p>Already have an account?
                    <a href="${pageContext.request.contextPath}/controller?command=show_user_login_page">Sign in</a>.
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