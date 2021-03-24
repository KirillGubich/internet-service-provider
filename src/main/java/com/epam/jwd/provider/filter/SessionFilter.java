package com.epam.jwd.provider.filter;

import com.epam.jwd.provider.model.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter
public class SessionFilter implements Filter {
    private static final String LOCALE_KEY = "locale";
    private static final String USER_ROLE_KEY = "userRole";
    private static final String DEFAULT_LOCALE = "en";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        processRole(session);
        processLocale(req, resp, session);
        chain.doFilter(request, response);
    }

    private void processLocale(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        String sessionLocale = (String) session.getAttribute(LOCALE_KEY);
        if (sessionLocale != null) {
            updateCookiesLocale(resp, sessionLocale);
            return;
        }
        Cookie[] cookies = req.getCookies();
        Optional<String> localeFromCookies = readLocaleFromCookies(cookies);
        if (localeFromCookies.isPresent()) {
            session.setAttribute(LOCALE_KEY, localeFromCookies.get());
        } else {
            session.setAttribute(LOCALE_KEY, DEFAULT_LOCALE);
            updateCookiesLocale(resp, DEFAULT_LOCALE);
        }
    }

    private void updateCookiesLocale(HttpServletResponse resp, String locale) {
        Cookie localeCookie = new Cookie(LOCALE_KEY, locale);
        resp.addCookie(localeCookie);
    }

    private void processRole(HttpSession session) {
        UserRole userRole = (UserRole) session.getAttribute(USER_ROLE_KEY);
        if (userRole == null) {
            session.setAttribute(USER_ROLE_KEY, UserRole.GUEST);
        }
    }

    private Optional<String> readLocaleFromCookies(Cookie[] cookies) {
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(c -> SessionFilter.LOCALE_KEY.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    @Override
    public void destroy() {

    }
}
