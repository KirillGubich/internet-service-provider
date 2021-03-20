<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
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
    <title>Subscriptions</title>
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
                                    <c:choose>
                                        <c:when test="${subscription.status eq 'REQUESTED'}">
                                            <a style="color:green;" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=approved">
                                                (approve)</a>
                                            <a style="color: red" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=denied">
                                                (deny)</a>
                                        </c:when>
                                        <c:when test="${subscription.status eq 'SUSPENDED'}">
                                            <a style="color:green;" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=approved">
                                                approve </a>
                                        </c:when>
                                        <c:when test="${subscription.status eq 'APPROVED'}">
                                            <a style="color: orange" href="${pageContext.request.contextPath}
                                        /controller?command=change_subscription_status&subId=${subscription.id}&userId=${subscription.userId}&status=suspended">
                                                suspend </a>
                                        </c:when>
                                    </c:choose>

                                </td>
                            </tr>
                        </table>
                        <br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2>${requestScope.infoMessage}</h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="content">
            <form action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="find_subscription">
                <div class="container">
                    <label>
                        <input type="text" placeholder="id" name="id" required>
                    </label><br>
                    <button type="submit" class="service_btn find_btn">Find</button>
                </div>
            </form>
            <div class="service">
                <a href="${pageContext.request.contextPath}/controller?command=view_subscription_requests" class="service_btn">Show requests</a>
            </div>
        </div>
        <br><br><br>
    </div>
    <footer class="page_footer">
        <div class="pagination">
            <c:if test="${currentPage != 1}">
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=show_subscription_settings_page&page=${currentPage - 1}">
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
                                    <a href="${pageContext.request.contextPath}/controller?command=show_subscription_settings_page&page=${i}">
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
                    <a href="${pageContext.request.contextPath}/controller?command=show_subscription_settings_page&page=${currentPage + 1}">
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
