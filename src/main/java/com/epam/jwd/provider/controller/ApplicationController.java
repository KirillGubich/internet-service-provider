package com.epam.jwd.provider.controller;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.HttpRequestContext;
import com.epam.jwd.provider.command.ResponseContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class ApplicationController extends HttpServlet {

    private static final String COMMAND_PARAMETER_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String commandName = req.getParameter(COMMAND_PARAMETER_NAME);
        final Command businessCommand = Command.of(commandName);
        final ResponseContext result = businessCommand.execute(HttpRequestContext.of(req));
        if (result.isRedirect()) {
            //todo
            resp.sendRedirect(result.getPage());
        } else {
            final RequestDispatcher dispatcher = req.getRequestDispatcher(result.getPage());
            dispatcher.forward(req, resp);
        }
    }
}
