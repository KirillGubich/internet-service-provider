<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet tariff settings">
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
        <%@include file="/WEB-INF/styles/profileFooter.css"%>
        <%@include file="/WEB-INF/styles/subscription.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <title>Tariffs</title>
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
<div class="user_page">
    <div class="profile">
        <div class="service_addition">
            <h2 class="service_header">Create tariff</h2>
            <div class="service_form">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type='hidden' name='command' value='tariff_service'/>
                    <input type='hidden' name='action' value='create'/>
                    <input class="city" type="text" placeholder="name" name="tariffName" required>
                    <input class="city" type="text" placeholder="description" name="description" required>
                    <select class="time_list" name="specialOffer" required>
                        <option value="">Special offer</option>
                        <option value="yes">yes</option>
                        <option value="no">no</option>
                    </select>
                    <input type="number" class="city" step="any" name="price" placeholder="price per day" required/>
                    <input type="number" class="city" step="any" name="downloadSpeed" placeholder="download speed" required/>
                    <input type="number" class="city" step="any" name="uploadSpeed" placeholder="upload speed" required/>
                    <button type="submit" class="servicebtn">Create</button>
                </form>
            </div>
        </div>
        <div class="service_addition">
            <h2 class="service_header">Update tariff</h2>
            <div class="service_form">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type='hidden' name='command' value='tariff_service'/>
                    <input type='hidden' name='action' value='update'/>
                    <select class="tariff_list" name="tariffName" required>
                        <option value="">name</option>
                        <c:if test="${not empty requestScope.tariffs}">
                            <c:forEach var="tariff" items="${requestScope.tariffs}">
                                <option value="${tariff.name}">${tariff.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <input class="city" type="text" placeholder="description" name="description" required>
                    <select class="time_list" name="specialOffer" required>
                        <option value="">Special offer</option>
                        <option value="yes">yes</option>
                        <option value="no">no</option>
                    </select>
                    <input type="number" class="city" step="any" name="price" placeholder="price per day" required/>
                    <input type="number" class="city" step="any" name="downloadSpeed" placeholder="download speed" required/>
                    <input type="number" class="city" step="any" name="uploadSpeed" placeholder="upload speed" required/>
                    <button type="submit" class="servicebtn">Update</button>
                </form>
            </div>
        </div>
        <div class="service_addition deleteTariff">
            <h2 class="service_header">Delete tariff</h2>
            <div class="service_form">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type='hidden' name='command' value='delete_tariff'/>
                    <select class="tariff_list" name="tariffName" required>
                        <option value="">name</option>
                        <c:if test="${not empty requestScope.tariffs}">
                            <c:forEach var="tariff" items="${requestScope.tariffs}">
                                <option value="${tariff.name}">${tariff.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <button type="submit" class="servicebtn">Delete</button>
                </form>
            </div>
        </div>
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
