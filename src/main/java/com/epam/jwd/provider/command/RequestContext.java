package com.epam.jwd.provider.command;

import javax.servlet.http.HttpServletRequest;

public interface RequestContext {

    void extractValues(HttpServletRequest request);

    void insertAttributes(HttpServletRequest request);

    void setAttribute(String name, Object obj);

    Object getAttribute(String name);

    Object getSessionAttribute(String name);

    void setSessionAttribute(String name, Object value);

    String getParameter(String name);

    void invalidateSession();
}
