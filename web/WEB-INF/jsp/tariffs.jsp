<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="tariffsPage"/>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet tariffs">
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/news.css"%>
        <%@include file="/WEB-INF/styles/profileFooter.css"%>
        <%@include file="/WEB-INF/styles/tariffs.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
<header>
    <nav class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller">Joi<span class="logoN">N</span>et</a>
        </div>
        <div class="menu">
            <ul class="nav">
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page"><fmt:message key="navigation.tariffs"/></a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_profile"><fmt:message key="navigation.subscribe"/></a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="user_page">
    <div class="profile">
        <div class="table-information">
            <c:choose>
                <c:when test="${not empty requestScope.tariffs}">
                    <c:forEach var="tariff" items="${requestScope.tariffs}">
                        <table class="table_col">
                            <colgroup>
                                <col style="background: rgba(38, 76, 114, 1)">
                            </colgroup>
                            <tr>
                                <td><fmt:message key="tariffInfo.name"/></td>
                                <td>${tariff.name}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="tariffInfo.description"/></td>
                                <td>${tariff.description}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="tariffInfo.price"/></td>
                                <td>${tariff.costPerDay}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="tariffInfo.speed"/></td>
                                <td>${tariff.downloadSpeed}/${tariff.uploadSpeed}</td>
                            </tr>
                        </table>
                        <br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="tariffs.absence"/></h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="content">
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
