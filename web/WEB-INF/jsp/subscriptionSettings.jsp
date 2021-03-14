<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet internet provider">
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
        <%@include file="/WEB-INF/styles/profileFooter.css"%>
        <%@include file="/WEB-INF/styles/usersForAdmin.css"%>
    </style>
    <title>Subscriptions</title>
</head>
<body>
<header>
    <div class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=show_profile">
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
        <div class="table-information">
            <c:if test="${not empty requestScope.userSubscriptions}">
                <h2 class="subscriptionsHeader">Users</h2>
                <c:forEach var="subscription" items="${requestScope.userSubscriptions}">
                    <table class="table_col">
                        <colgroup>
                            <col style="background: rgba(38, 76, 114, 1)">
                        </colgroup>
                        <tr>
                            <td>ID</td>
                            <td>${subscription.id}</td>
                        </tr>
                        <tr>
                            <td>User ID</td>
                            <td>${subscription.userId}</td>
                        </tr>
                        <tr>
                            <td>Tariff</td>
                            <td>${subscription.tariffName}</td>
                        </tr>
                        <tr>
                            <td>Tariff description</td>
                            <td>${subscription.tariffDescription}</td>
                        </tr>
                        <tr>
                            <td>Start date</td>
                            <td>${subscription.startDate}</td>
                        </tr>
                        <tr>
                            <td>End date</td>
                            <td>${subscription.endDate}</td>
                        </tr>
                        <tr>
                            <td>Cost</td>
                            <td>${subscription.price}</td>
                        </tr>
                        <tr>
                            <td>Address</td>
                            <td>${subscription.address.city}, ${subscription.address.address}</td>
                        </tr>
                        <tr>
                            <td>Status</td>
                            <td>
                                    ${subscription.status}
                                <c:if test="${subscription.status=='REQUESTED'}">
                                    <a href="${pageContext.request.contextPath}
                                        /controller?command=cancel_subscription&subId=${subscription.id}&userId=${subscription.userId}">
                                        (сlick here to cancel)</a>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                    <br>
                </c:forEach>
            </c:if>
        </div>
        <div class="content">
            <form action="#">
                <div class="container">
                    <label>
                        <input type="text" placeholder="id" name="id" required>
                    </label><br>
                    <button type="submit" class="service_btn find_btn">Find</button>
                </div>
            </form>
            <div class="service">
                <a href="#" class="service_btn">Show requests</a>
            </div>
        </div>
        <br><br><br>
    </div>
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
