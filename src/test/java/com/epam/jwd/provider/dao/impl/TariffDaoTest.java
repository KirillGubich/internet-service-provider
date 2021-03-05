package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.model.entity.Tariff;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TariffDaoTest {

    @Test
    public void readAll() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        TariffDao tariffDao = TariffDao.INSTANCE;
        Optional<List<Tariff>> tariffs = tariffDao.readAll();
        tariffs.ifPresent(tariffList -> tariffList.forEach(System.out::println));
        ProviderConnectionPool.getInstance().destroyPool();
    }

    @Test
    public void read() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        TariffDao tariffDao = TariffDao.INSTANCE;
        Tariff tariff = Tariff.builder()
                .withName("Turbo")
                .build();
        System.out.println(tariffDao.findByName(tariff));
        ProviderConnectionPool.getInstance().destroyPool();
    }

    @Test
    public void create() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        TariffDao tariffDao = TariffDao.INSTANCE;
        Tariff tariff = Tariff.builder()
                .withName("Gamer_MAX")
                .withDescription("For gamers")
                .withSpecialOffer(false)
                .withCostPerDay(new BigDecimal("1.99"))
                .withDownloadSpeed(200.0)
                .withUploadSpeed(100.0)
                .build();
        tariffDao.create(tariff);
        ProviderConnectionPool.getInstance().destroyPool();
    }

    @Test
    public void delete() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        TariffDao tariffDao = TariffDao.INSTANCE;
        Tariff tariff = Tariff.builder()
                .withId(2)
                .build();
        tariffDao.delete(tariff);
        ProviderConnectionPool.getInstance().destroyPool();
    }

    @Test
    public void update() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        TariffDao tariffDao = TariffDao.INSTANCE;
        Tariff tariff = Tariff.builder()
                .withId(3)
                .withName("Gamer_MAX")
                .withDescription("For gamers")
                .withSpecialOffer(false)
                .withCostPerDay(new BigDecimal("2.49"))
                .withDownloadSpeed(200.0)
                .withUploadSpeed(100.0)
                .build();
        tariffDao.update(tariff);
        ProviderConnectionPool.getInstance().destroyPool();
    }
}