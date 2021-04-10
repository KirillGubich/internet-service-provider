package com.epam.jwd.provider.listener;

import com.epam.jwd.provider.exception.PropertyLoadingException;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ProviderConnectionPool.getInstance().init();
            LOGGER.info("Connection pool initialized successfully");
        } catch (SQLException | PropertyLoadingException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ProviderConnectionPool.getInstance().destroy();
            LOGGER.info("Connection pool destroyed successfully");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
