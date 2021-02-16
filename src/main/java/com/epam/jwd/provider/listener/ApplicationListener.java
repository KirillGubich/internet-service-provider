package com.epam.jwd.provider.listener;

import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ProviderConnectionPool.getInstance().init();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo log or exception
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ProviderConnectionPool.getInstance().destroyPool();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo log or exception
        }
    }
}
