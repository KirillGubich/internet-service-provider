package com.epam.jwd.provider.filter;

import com.epam.jwd.provider.model.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter
public class UserFilter implements Filter {
    private static final List<String> pagesForAuthorizedUsersOnly = new ArrayList<>();
    private static final String USER_ROLE_SESSION_ATTRIBUTE_NAME = "userRole";
    private static final String LOGIN_PAGE_LINK = "/controller?command=show_user_login_page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pagesForAuthorizedUsersOnly.add("show_profile");
        pagesForAuthorizedUsersOnly.add("show_settings_page");
        pagesForAuthorizedUsersOnly.add("show_subscription_page");
        pagesForAuthorizedUsersOnly.add("subscribe");
        pagesForAuthorizedUsersOnly.add("logout");
        pagesForAuthorizedUsersOnly.add("cancel_subscription");
        pagesForAuthorizedUsersOnly.add("change_password");
        pagesForAuthorizedUsersOnly.add("contact_support");
        pagesForAuthorizedUsersOnly.add("top_up_balance");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        UserRole userRole = (UserRole) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE_NAME);
        if (!UserRole.USER.equals(userRole)) {
            String queryString = req.getQueryString();
            for (String page : pagesForAuthorizedUsersOnly) {
                if (queryString != null && queryString.contains(page)) {
                    resp.sendRedirect(LOGIN_PAGE_LINK);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
