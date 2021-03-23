package com.epam.jwd.provider.command;

/**
 * Combines all commands with a single execution method.
 *
 * @author Kirill Gubich
 */
public interface Command {

    /**
     * Executes command
     *
     * @param request request context for command
     * @return response context
     */
    ResponseContext execute(RequestContext request);

    /**
     * Returns command by her name
     *
     * @param name command name
     * @return corresponding command
     */
    static Command of(String name) {
        return CommandManager.of(name);
    }

}
