package com.epam.jwd.provider.command;

import javax.servlet.http.HttpServletRequest;

/**
 * <code>RequestContext</code> - allows to wrap the request handling.
 * Gives the opportunity to extract request values, work with them and insert values back to request.
 * Provides methods for working with parameters and attributes.
 *
 * @author Kirill Gubich
 */
public interface RequestContext {

    /**
     * Extract values from request
     *
     * @param request http request
     */
    void extractValues(HttpServletRequest request);

    /**
     * Insert values to request
     *
     * @param request http request
     */
    void insertAttributes(HttpServletRequest request);

    /**
     * Sets request attribute
     *
     * @param name attribute name
     * @param obj  attribute
     */
    void setAttribute(String name, Object obj);

    /**
     * Returns request attribute
     *
     * @param name attribute name
     * @return attribute
     */
    Object getAttribute(String name);

    /**
     * Returns session attribute
     *
     * @param name attribute name
     * @return attribute
     */
    Object getSessionAttribute(String name);

    /**
     * Sets session attribute
     *
     * @param name  attribute name
     * @param value attribute
     */
    void setSessionAttribute(String name, Object value);

    /**
     * Returns request parameter
     *
     * @param name parameter name
     * @return parameter
     */
    String getParameter(String name);

    /**
     * Invalidates current session
     */
    void invalidateSession();
}
