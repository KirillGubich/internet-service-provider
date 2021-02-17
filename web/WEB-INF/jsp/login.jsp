<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="/controller">
    <div class="container">
        <h1 class="logo">
            <a href="/controller">JoiNet</a>
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
            <p>Don't have an account? <a href="/controller?command=sign_up">Sign up</a>.</p>
        </div>
    </div>
</form>
</body>
</html>
