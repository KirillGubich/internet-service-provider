<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="profilePage"/>
<html lang="${language}">
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
    <title><fmt:message key="page.title"/></title>
</head>
<body>
<header>
    <div class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=show_profile">Joi<span class="logoN">N</span>et</a>
        </div>
        <nav class="menu">
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
        </nav>
    </div>
</header>
<div class="user_page">
    <div class="profile">
        <div class="table-information">
            <c:choose>
                <c:when test="${not empty requestScope.userSubscriptions}">
                    <h2 class="subscriptionsHeader"><fmt:message key="subscriptions.header"/></h2>
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
                                <td><fmt:message key="subscriptionInfo.tariff"/></td>
                                <td>${subscription.tariffName}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="subscriptionInfo.tariffDescription"/></td>
                                <td>${subscription.tariffDescription}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="subscriptionInfo.startDate"/></td>
                                <td><ctg:format-date date="${subscription.startDate}" lang="${language}"/></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="subscriptionInfo.endDate"/></td>
                                <td><ctg:format-date date="${subscription.endDate}" lang="${language}"/></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="subscriptionInfo.cost"/></td>
                                <td>${subscription.price}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="subscriptionInfo.address"/></td>
                                <td>${subscription.address.city}, ${subscription.address.address}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="subscriptionInfo.status"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${subscription.status == 'REQUESTED'}">
                                            <span style="color: #2e7ebd"><fmt:message key="subscription.status.requested"/></span>
                                            <a href="${pageContext.request.contextPath}
                                        /controller?command=cancel_subscription&subId=${subscription.id}&userId=${subscription.userId}">
                                                (<fmt:message key="subscription.cancel.caption"/>)</a>
                                        </c:when>
                                        <c:when test="${subscription.status == 'APPROVED'}">
                                            <span style="color: green"><fmt:message key="subscription.status.approved"/></span>
                                        </c:when>
                                        <c:when test="${subscription.status == 'SUSPENDED'}">
                                            <span style="color: orange"><fmt:message key="subscription.status.suspended"/></span>
                                        </c:when>
                                        <c:when test="${subscription.status == 'DENIED'}">
                                            <span style="color: red"><fmt:message key="subscription.status.denied"/></span>
                                        </c:when>
                                        <c:when test="${subscription.status == 'CANCELED'}">
                                            <span style="color: dimgrey"><fmt:message key="subscription.status.canceled"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            ${subscription.status}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                        <br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="subscription.absence"/></h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="content">
            <div class="service">
                <a href="${pageContext.request.contextPath}/controller?command=show_subscription_page"
                   class="service_btn"><fmt:message key="service.button"/></a>
            </div>
            <div class="news-card-inner">
                <div class="news-card__header" tabindex="0">
                    <div class="clearfix">
                        <h3 class="news-card__title"><fmt:message key="specialOffers.caption"/></h3>
                    </div>
                </div>
                <ul class="news-card-news">
                    <c:choose>
                        <c:when test="${not empty requestScope.specialOffers}">
                            <c:forEach var="specialOffer" items="${requestScope.specialOffers}">
                                <li class="news-card-news-item ng-star-inserted">
                                    <div class="specialOffer">
                                            ${specialOffer.name} <fmt:message key="onlyFor.caption"/> ${specialOffer.costPerDay} <fmt:message key="perDay.caption"/>!
                                    </div>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="specialOffer">
                                <fmt:message key="specialOffers.absence"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
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
