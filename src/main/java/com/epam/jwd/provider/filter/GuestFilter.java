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
public class GuestFilter implements Filter {

    private static final List<String> pagesForAuthorizedUsersOnly = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pagesForAuthorizedUsersOnly.add("command=show_profile");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        UserRole userRole = (UserRole) session.getAttribute("userRole");
        if (UserRole.GUEST.equals(userRole) || userRole == null) {
            String queryString = req.getQueryString();
            if (pagesForAuthorizedUsersOnly.contains(queryString)) {
                resp.sendRedirect("/controller?command=show_user_login_page");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
