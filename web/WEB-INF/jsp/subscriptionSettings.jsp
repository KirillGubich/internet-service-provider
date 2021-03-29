<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="subscriptionSettingsPage"/>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet subscription settings">
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
        <%@include file="/WEB-INF/styles/paginationFotter.css"%>
        <%@include file="/WEB-INF/styles/usersForAdmin.css"%>
    </style>
    <title><fmt:message key="page.title"/></title>
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
                    <h2 class="subscriptionsHeader"><fmt:message key="page.title"/></h2>
                    <c:forEach var="subscription" items="${requestScope.userSubscriptions}">
                        <table class="table_col">
                            <colgroup>
                                <col style="background: rgba(38, 76, 114, 1)">
                            </colgroup>
                            <tr>
                                <td>ID</td>
                                <td>
                                        ${subscription.id}
                                    <a href="${pageContext.request.contextPath}
                                    /controller?command=delete_subscription&subId=${subscription.id}">
                                        (<fmt:message key="delete.caption"/>)
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td><fmt:message key="user.id.placeholder"/></td>
                                <td>${subscription.userId}</td>
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
                                        <c:when test="${subscription.status eq 'REQUESTED'}">
                                            <fmt:message key="subscription.status.requested"/>
                                            <a style="color:green;" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=approved">
                                                (<fmt:message key="status.action.approve"/>)</a>
                                            <a style="color: red" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=denied">
                                                (<fmt:message key="status.action.deny"/>)</a>
                                        </c:when>
                                        <c:when test="${subscription.status eq 'SUSPENDED'}">
                                            <fmt:message key="subscription.status.suspended"/>
                                            <a style="color:green;" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=approved">
                                                (<fmt:message key="status.action.approve"/>) </a>
                                        </c:when>
                                        <c:when test="${subscription.status eq 'APPROVED'}">
                                            <fmt:message key="subscription.status.approved"/>
                                            <a style="color: orange" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=suspended">
                                                <fmt:message key="status.action.suspend"/> </a>
                                        </c:when>
                                        <c:when test="${subscription.status eq 'DENIED'}">
                                            <fmt:message key="subscription.status.denied"/>
                                        </c:when>
                                        <c:when test="${subscription.status eq 'CANCELED'}">
                                            <fmt:message key="subscription.status.canceled"/>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                        <br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="info.message"/></h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="content">
            <form action="${pageContext.request.contextPath}/controller" method="get" class="findSubscription">
                <input type="hidden" name="command" value="find_subscription">
                <div class="container">
                    <label>
                        <input type="text" placeholder="id" name="id" required>
                    </label><br>
                    <button type="submit" class="service_btn find_btn"><fmt:message key="button.find"/></button>
                </div>
            </form>
            <form action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="find_subscription">
                <div class="container">
                    <label>
                        <input type="text" placeholder="<fmt:message key="user.id.placeholder"/>" name="userId" required>
                    </label><br>
                    <button type="submit" class="service_btn find_btn"><fmt:message key="button.find"/></button>
                </div>
            </form>
            <div class="service">
                <a href="${pageContext.request.contextPath}/controller?command=view_subscription_requests" class="service_btn">
                    <fmt:message key="button.showRequests"/>
                </a>
            </div>
            <div class="service">
                <a href="${pageContext.request.contextPath}/controller?command=show_subscription_settings_page" class="service_btn">
                    <fmt:message key="subscriptions.header"/>
                </a>
            </div>
        </div>
        <br><br><br>
    </div>
    <footer class="page_footer">
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=${requestScope.command}&page=${currentPage - 1}">
                        <fmt:message key="page.previous"/>
                    </a>
                </td>
            </c:if>
            <table border="1" cellpadding="5" cellspacing="5">
                <tr>
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <a href="${pageContext.request.contextPath}/controller?command=${requestScope.command}&page=${i}">
                                            ${i}
                                    </a>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>
            <c:if test="${currentPage lt noOfPages}">
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=${requestScope.command}&page=${currentPage + 1}">
                        <fmt:message key="page.next"/>
                    </a>
                </td>
            </c:if>
        </div>
        <br><br><br>
        <div class="socialButtons">
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
        </div>
    </footer>
</div>
</body>
</html>
