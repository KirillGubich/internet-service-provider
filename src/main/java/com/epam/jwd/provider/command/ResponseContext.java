package com.epam.jwd.provider.command;

/**
 * Wrapper for response context. Contains information about page and redirection.
 *
 * @author Kirill Gubich
 */
public interface ResponseContext {

    /**
     * Returns page for transition
     *
     * @return page for transition
     */
    String getPage();

    /**
     * Returns true if it is redirect
     *
     * @return true if it is redirect
     */
    boolean isRedirect();
}
