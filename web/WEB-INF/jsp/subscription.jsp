<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
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
    <title>Service addition</title>
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
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_settings_page">Settings</a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=show_tariffs_page">Tariffs</a>
                </li>
                <li>
                    <a class="link" href="${pageContext.request.contextPath}/controller?command=logout">Sign out</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="user_page">
    <div class="profile">
        <div class="service_addition">
            <h2 class="service_header">Add service</h2>
            <div class="service_form">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type='hidden' name='command' value='subscribe'/>
                    <label>
                        <select class="tariff_list" name="tariff" required>
                            <option value="">Tariff</option>
                            <c:if test="${not empty requestScope.tariffs}">
                                <c:forEach var="tariff" items="${requestScope.tariffs}">
                                    <option value="${tariff.name}">${tariff.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </label>
                    <label>
                        <select class="time_list" name="validity" required>
                            <option value="">Validity</option>
                            <option value="15">15</option>
                            <option value="30">30</option>
                            <option value="60">60</option>
                            <option value="90">90</option>
                            <option value="180">180</option>
                        </select>
                    </label>
                    <label>
                        <input class="city" type="text" placeholder="City" name="city" required>
                    </label>
                    <label>
                        <input class="address" type="text" placeholder="Address" name="address" required>
                    </label>
                    <button type="submit" class="servicebtn">Submit</button>
                    <br>
                    <h4 style="color: red">${requestScope.errorMessage}</h4>
                </form>
            </div>
        </div>
        <div class="content">
            <div class="news-card-inner">
                <div class="news-card-header" tabindex="0">
                    <div class="clearfix">
                        <h3 class="news-card-title">Special offers</h3>
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
