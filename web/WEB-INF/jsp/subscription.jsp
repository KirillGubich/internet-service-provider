<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="subscriptionPage"/>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet service addition page">
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/news.css"%>
        <%@include file="/WEB-INF/styles/profileFooter.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
        <%@include file="/WEB-INF/styles/subscription.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
<header>
    <nav class="navigation">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=show_profile">Joi<span class="logoN">N</span>et</a>
        </div>
        <div class="menu">
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
        </div>
    </nav>
</header>
<div class="user_page">
    <div class="profile">
        <div class="service_addition">
            <h2 class="service_header"><fmt:message key="service.header"/></h2>
            <div class="service_form">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type='hidden' name='command' value='subscribe'/>
                    <label>
                        <select class="tariff_list" name="tariff" required>
                            <option value=""><fmt:message key="form.tariff"/></option>
                            <c:if test="${not empty requestScope.tariffs}">
                                <c:forEach var="tariff" items="${requestScope.tariffs}">
                                    <option value="${tariff.name}">${tariff.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </label>
                    <label>
                        <select class="time_list" name="validity" required>
                            <option value=""><fmt:message key="form.validity"/></option>
                            <option value="15">15</option>
                            <option value="30">30</option>
                            <option value="60">60</option>
                            <option value="90">90</option>
                            <option value="180">180</option>
                        </select>
                    </label>
                    <label>
                        <input class="city" type="text" placeholder="<fmt:message key="form.city"/>" name="city" required>
                    </label>
                    <label>
                        <input class="address" type="text" placeholder="<fmt:message key="form.address"/>" name="address" required>
                    </label>
                    <button type="submit" class="servicebtn"><fmt:message key="button.submit.caption"/></button>
                    <br>
                    <c:if test="${requestScope.errorMessage eq true}">
                        <h4 style="color: red"><fmt:message key="error.message"/></h4>
                    </c:if>
                </form>
            </div>
        </div>
        <div class="content">
            <div class="news-card-inner">
                <div class="news-card-header" tabindex="0">
                    <div class="clearfix">
                        <h3 class="news-card-title"><fmt:message key="specialOffers.caption"/></h3>
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
