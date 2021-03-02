package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.dao.CommonDao;
import com.epam.jwd.provider.model.entity.Tariff;

import java.util.List;
import java.util.Optional;

public enum TariffDao implements CommonDao<Tariff> {
    INSTANCE;

    private static final String FIND_ALL_TARIFFS_SQL = "SELECT id, name, price, download_speed, " +
            "upload_speed FROM tariff_list";

    @Override
    public Optional<List<Tariff>> readAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Tariff> update(Tariff entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Tariff entity) {

    }

    @Override
    public void create(Tariff entity) {
    }

    @Override
    public Optional<Tariff> read(Tariff entity) {
        return Optional.empty();
    }
}
