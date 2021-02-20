package com.epam.jwd.provider.filter;

import com.epam.jwd.provider.model.UserRole;

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

@WebFilter(urlPatterns = {"*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession sessionTest = req.getSession(false);
        HttpSession session = req.getSession();
        String queryString = req.getQueryString();

        if (!("command=show_profile").equalsIgnoreCase(queryString)) {
            chain.doFilter(request, response);
            return;
        }

        Object userRole = session.getAttribute("userRole");
        if (userRole == null || userRole == UserRole.GUEST) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/controller?command=show_user_login_page");
            dispatcher.forward(req, resp);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
