<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<header>
    <form action="/controller">
        <div class="container">
            <h1 class="logo">
                <a href="/controller">JoiNet</a>
            </h1>
            <input type='hidden' name='command' value='sign_up'/>
            <label>
                <input type="text" placeholder="Enter Email" name="userLogin" required>
            </label><br>
            <label>
                <input type="password" placeholder="Enter Password" name="userPassword" required>
            </label><br>
            <label>
                <input type="password" placeholder="Repeat Password" name="userRepPassword" required>
            </label><br>
            <button type="submit" class="registerbtn">Create account</button>
            <div class="signin">
                <p>Already have an account? <a href="/controller?command=show_user_login_page">Sign in</a>.</p>
            </div>
            <h2>${requestScope.errorMessage}</h2>
        </div>
    </form>
</header>
</html>
