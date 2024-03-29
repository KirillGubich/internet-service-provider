package com.epam.jwd.provider.filter;

import com.epam.jwd.provider.model.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
public class AdminFilter implements Filter {
    private static final List<String> pagesForAdminOnly = new ArrayList<>();
    private static final String USER_ROLE_SESSION_ATTRIBUTE_NAME = "userRole";
    private static final String LOGIN_PAGE_LINK = "/controller?command=show_user_login_page";

    @Override
    public void init(FilterConfig filterConfig) {
        pagesForAdminOnly.add("change_subscription_status");
        pagesForAdminOnly.add("change_user_status");
        pagesForAdminOnly.add("delete_tariff");
        pagesForAdminOnly.add("find_subscription");
        pagesForAdminOnly.add("find_user");
        pagesForAdminOnly.add("tariff_service");
        pagesForAdminOnly.add("view_debtors");
        pagesForAdminOnly.add("view_subscription_requests");
        pagesForAdminOnly.add("show_admin_page");
        pagesForAdminOnly.add("show_subscription_settings_page");
        pagesForAdminOnly.add("show_tariff_settings_page");
        pagesForAdminOnly.add("show_users_for_admin_page");
        pagesForAdminOnly.add("delete_account");
        pagesForAdminOnly.add("delete_subscription");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        UserRole userRole = (UserRole) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE_NAME);
        if (!UserRole.ADMIN.equals(userRole)) {
            String queryString = req.getQueryString();
            for (String page : pagesForAdminOnly) {
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
