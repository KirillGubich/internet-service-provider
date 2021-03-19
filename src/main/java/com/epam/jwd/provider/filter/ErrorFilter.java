package com.epam.jwd.provider.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ErrorFilter implements Filter {

    private static final String ERROR_JSP_PATH = "/WEB-INF/jsp/error.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Throwable throwable) {
            final RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_JSP_PATH);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
