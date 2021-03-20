<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${sessionScope.locale}"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="tariffSettingsPage"/>
<html lang="${language}">
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
        <div class="service_addition">
            <h2 class="service_header"><fmt:message key="header.create"/></h2>
            <div class="service_form">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type='hidden' name='command' value='tariff_service'/>
                    <input type='hidden' name='action' value='create'/>
                    <input class="city" type="text" placeholder="<fmt:message key="field.name"/>" name="tariffName" required>
                    <input class="city" type="text" placeholder="<fmt:message key="field.description"/>" name="description" required>
                    <select class="time_list" name="specialOffer" required>
                        <option value=""><fmt:message key="field.specialOffer"/></option>
                        <option value="yes"><fmt:message key="field.yes"/></option>
                        <option value="no"><fmt:message key="field.no"/></option>
                    </select>
                    <input type="number" class="city" step="any" name="price" placeholder="<fmt:message key="field.price"/>" required/>
                    <input type="number" class="city" step="any" name="downloadSpeed" placeholder="<fmt:message key="field.downloadSpeed"/>" required/>
                    <input type="number" class="city" step="any" name="uploadSpeed" placeholder="<fmt:message key="field.uploadSpeed"/>" required/>
                    <button type="submit" class="servicebtn"><fmt:message key="button.create"/></button>
                </form>
            </div>
        </div>
        <div class="service_addition">
            <h2 class="service_header"><fmt:message key="header.update"/></h2>
            <div class="service_form">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type='hidden' name='command' value='tariff_service'/>
                    <input type='hidden' name='action' value='update'/>
                    <select class="tariff_list" name="tariffName" required>
                        <option value=""><fmt:message key="field.name"/></option>
                        <c:if test="${not empty requestScope.tariffs}">
                            <c:forEach var="tariff" items="${requestScope.tariffs}">
                                <option value="${tariff.name}">${tariff.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <input class="city" type="text" placeholder="<fmt:message key="field.description"/>" name="description" required>
                    <select class="time_list" name="specialOffer" required>
                        <option value=""><fmt:message key="field.specialOffer"/></option>
                        <option value="yes"><fmt:message key="field.yes"/></option>
                        <option value="no"><fmt:message key="field.no"/></option>
                    </select>
                    <input type="number" class="city" step="any" name="price" placeholder="<fmt:message key="field.price"/>" required/>
                    <input type="number" class="city" step="any" name="downloadSpeed" placeholder="<fmt:message key="field.downloadSpeed"/>" required/>
                    <input type="number" class="city" step="any" name="uploadSpeed" placeholder="<fmt:message key="field.uploadSpeed"/>" required/>
                    <button type="submit" class="servicebtn"><fmt:message key="button.update"/></button>
                </form>
            </div>
        </div>
        <div class="service_addition deleteTariff">
            <h2 class="service_header"><fmt:message key="header.delete"/></h2>
            <div class="service_form">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type='hidden' name='command' value='delete_tariff'/>
                    <select class="tariff_list" name="tariffName" required>
                        <option value=""><fmt:message key="field.name"/></option>
                        <c:if test="${not empty requestScope.tariffs}">
                            <c:forEach var="tariff" items="${requestScope.tariffs}">
                                <option value="${tariff.name}">${tariff.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <button type="submit" class="servicebtn"><fmt:message key="button.delete"/></button>
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
