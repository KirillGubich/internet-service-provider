package com.epam.jwd.provider.filter;

import com.epam.jwd.provider.model.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminFilter implements Filter {

    private static final List<String> pagesForAdminOnly = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
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
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        UserRole userRole = (UserRole) session.getAttribute("userRole");
        if (!UserRole.ADMIN.equals(userRole)) {
            String queryString = req.getQueryString();
            for (String page : pagesForAdminOnly) {
                if (queryString != null && queryString.contains(page)) {
                    resp.sendRedirect("/controller?command=show_user_login_page");
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
