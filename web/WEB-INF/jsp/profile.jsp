<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet user profile">
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/news.css"%>
        <%@include file="/WEB-INF/styles/profileFooter.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <title>Profile</title>
</head>
<body>
<header>
    <div class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=show_profile">Joi<span class="logoN">N</span>et</a>
        </div>
        <nav class="menu">
            <ul class="nav">
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=show_settings_page">Settings</a></li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">Tariffs</a>
                </li>
                <li><a class="link" href="${pageContext.request.contextPath}/controller?command=logout">Sign out</a></li>
            </ul>
        </nav>
    </div>
</header>
<div class="user_page">
    <div class="profile">
        <div class="table-information">
            <c:choose>
                <c:when test="${not empty requestScope.userSubscriptions}">
                    <h2 class="subscriptionsHeader">Subscriptions</h2>
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
                                <td>${subscription.status}</td>
                            </tr>
                        </table>
                        <br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2>You have no subscriptions</h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="content">
            <div class="service">
                <a href="${pageContext.request.contextPath}/controller?command=show_subscription_page"
                   class="service_btn">Add service</a>
            </div>
            <div class="news-card-inner">
                <div class="news-card__header" tabindex="0">
                    <div class="clearfix">
                        <h3 class="news-card__title">Special offers</h3>
                    </div>
                </div>
                <ul class="news-card-news">
                    <c:choose>
                        <c:when test="${not empty requestScope.specialOffers}">
                            <c:forEach var="specialOffer" items="${requestScope.specialOffers}">
                                <li class="news-card-news-item ng-star-inserted">
                                    <div class="specialOffer">
                                            ${specialOffer.name} only for ${specialOffer.costPerDay} per day!
                                    </div>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="specialOffer">
                                Unfortunately, there are no special offers now.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
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
