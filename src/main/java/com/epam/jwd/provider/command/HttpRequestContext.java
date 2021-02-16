package com.epam.jwd.provider.command;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestContext implements RequestContext {

    private final HttpServletRequest request;

    private HttpRequestContext(HttpServletRequest request) {
        this.request = request;
    }

    public static RequestContext of(HttpServletRequest request) {
        return new HttpRequestContext(request);
    }

    @Override
    public void setAttribute(String name, Object obj) {
        request.setAttribute(name, obj);
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public void invalidateSession() {

    }

    @Override
    public void setSessionAttribute(String name, Object value) {

    }
}
