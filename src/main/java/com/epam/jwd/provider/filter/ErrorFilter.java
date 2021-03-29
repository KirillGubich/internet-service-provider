package com.epam.jwd.provider.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter
public class ErrorFilter implements Filter {
    private static final String ERROR_JSP_PATH = "/WEB-INF/jsp/error.jsp";
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage());
            final RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_JSP_PATH);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
