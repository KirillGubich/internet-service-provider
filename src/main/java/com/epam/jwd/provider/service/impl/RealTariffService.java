package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.TariffDao;
import com.epam.jwd.provider.model.BaseEntity;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.model.entity.Tariff;
import com.epam.jwd.provider.service.TariffService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public enum RealTariffService implements TariffService {
    INSTANCE;

    private TariffDao tariffDao = TariffDao.getInstance();

    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Override
    public List<TariffDto> findAll() {
        Optional<List<Tariff>> tariffs = tariffDao.readAll();
        return tariffs.map(tariffList -> tariffList.stream()
                .map(this::convertToDto)
                .collect(toList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public List<TariffDto> findSpecialOffers() {
        Optional<List<Tariff>> tariffs = tariffDao.readAll();
        return tariffs.map(tariffList -> tariffList.stream()
                .filter(Tariff::getSpecialOffer)
                .map(this::convertToDto)
                .collect(toList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public Optional<TariffDto> findByName(String name) {
        Optional<Tariff> tariff = tariffDao.findByName(name);
        return tariff.map(this::convertToDto);
    }

    @Override
    public Optional<Integer> findTariffId(String name) {
        Optional<Tariff> tariff = tariffDao.findByName(name);
        return tariff.map(BaseEntity::getId);
    }

    @Override
    public void create(TariffDto dto) {
        tariffDao.create(convertToTariff(dto));
    }

    @Override
    public Optional<TariffDto> save(TariffDto dto) {
        Optional<Tariff> oldTariff = tariffDao.update(convertToTariff(dto));
        return oldTariff.map(this::convertToDto);
    }

    @Override
    public void delete(TariffDto dto) {
        tariffDao.delete(convertToTariff(dto));
    }

    private TariffDto convertToDto(Tariff tariff) {
        return TariffDto.builder()
                .withName(tariff.getName())
                .withDescription(tariff.getDescription())
                .withSpecialOffer(tariff.getSpecialOffer())
                .withCostPerDay(tariff.getCostPerDay())
                .withDownloadSpeed(tariff.getDownloadSpeed())
                .withUploadSpeed(tariff.getUploadSpeed())
                .build();
    }

    private Tariff convertToTariff(TariffDto dto) {
        return Tariff.builder()
                .withName(dto.getName())
                .withDescription(dto.getDescription())
                .withSpecialOffer(dto.getSpecialOffer())
                .withCostPerDay(dto.getCostPerDay())
                .withDownloadSpeed(dto.getDownloadSpeed())
                .withUploadSpeed(dto.getUploadSpeed())
                .build();
    }
}
