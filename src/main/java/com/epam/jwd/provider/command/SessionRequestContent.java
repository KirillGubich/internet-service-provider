package com.epam.jwd.provider.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

public class SessionRequestContent implements RequestContext {
    private final HashMap<String, Object> requestAttributes;
    private final HashMap<String, String> requestParameters;
    private final HashMap<String, Object> sessionAttributes;
    private HttpSession session;

    private SessionRequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    public static SessionRequestContent createSessionRequestContent() {
        return new SessionRequestContent();
    }

    @Override
    public void extractValues(HttpServletRequest request) {
        extractRequestParameters(request);
        extractSessionAttributes(request);
        session = request.getSession();
    }

    @Override
    public void insertAttributes(HttpServletRequest request) {
        insertRequestAttributes(request);
        insertSessionAttributes(request);
    }

    @Override
    public void setAttribute(String name, Object obj) {
        requestAttributes.put(name, obj);
    }

    @Override
    public Object getAttribute(String name) {
        return requestAttributes.get(name);
    }

    @Override
    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    @Override
    public void invalidateSession() {
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    @Override
    public String getParameter(String name) {
        return requestParameters.get(name);
    }

    private void extractRequestParameters(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String element = parameterNames.nextElement();
            requestParameters.put(element, request.getParameter(element));
        }
    }

    private void extractSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            sessionAttributes.put(element, session.getAttribute(element));
        }
    }

    private void insertSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        for (String key : sessionAttributes.keySet()) {
            session.setAttribute(key, sessionAttributes.get(key));
        }
    }

    private void insertRequestAttributes(HttpServletRequest request) {
        for (String key : requestAttributes.keySet()) {
            request.setAttribute(key, requestAttributes.get(key));
        }
    }
}
