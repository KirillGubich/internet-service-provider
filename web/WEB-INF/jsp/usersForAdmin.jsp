<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Joinet users for admin">
    <script src="https://kit.fontawesome.com/0590a78e7b.js" crossorigin="anonymous"></script>
    <style>
        <%@include file="/WEB-INF/styles/navigation.css"%>
        <%@include file="/WEB-INF/styles/profile.css"%>
        <%@include file="/WEB-INF/styles/paginationFotter.css"%>
        <%@include file="/WEB-INF/styles/usersForAdmin.css"%>
    </style>
    <title>Users</title>
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
                <c:when test="${not empty requestScope.users}">
                    <h2 class="subscriptionsHeader">Users</h2>
                    <c:forEach var="user" items="${requestScope.users}">
                        <table class="table_col">
                            <colgroup>
                                <col style="background: rgba(38, 76, 114, 1)">
                            </colgroup>
                            <tr>
                                <td>ID</td>
                                <td>${user.id}</td>
                            </tr>
                            <tr>
                                <td>Login</td>
                                <td>${user.login}</td>
                            </tr>
                            <c:if test="${not empty user.balance}">
                                <tr>
                                    <td>Balance</td>
                                    <td>${user.balance}</td>
                                </tr>
                                <tr>
                                    <td>Status</td>
                                    <c:choose>
                                        <c:when test="${user.active eq false}">
                                            <td><span style="color:red">Blocked</span>
                                                <a href="${pageContext.request.contextPath}/controller?command=change_user_status&id=${user.id}&active=unblock">(unblock)</a>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <span style="color:green">Active</span>
                                                <a href="${pageContext.request.contextPath}/controller?command=change_user_status&id=${user.id}&active=block">(block)</a>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:if>
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
                <input type='hidden' name='command' value='find_user'/>
                <div class="container">
                    <label>
                        <input type="text" placeholder="id" name="id" required>
                    </label><br>
                    <button type="submit" class="service_btn find_btn">Find</button>
                </div>
            </form>
            <div class="service">
                <a href="${pageContext.request.contextPath}/controller?command=view_debtors" class="service_btn">Show debtors</a>
            </div>
        </div>
        <br><br><br>
    </div>
    <footer class="page_footer">
        <div class="pagination">
            <c:if test="${currentPage != 1}">
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=show_users_for_admin_page&page=${currentPage - 1}">
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
                                    <a href="${pageContext.request.contextPath}/controller?command=show_users_for_admin_page&page=${i}">
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
                    <a href="${pageContext.request.contextPath}/controller?command=show_users_for_admin_page&page=${currentPage + 1}">
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
