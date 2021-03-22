package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.TariffDao;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.model.entity.Tariff;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RealTariffServiceTest {

    private static RealTariffService service;
    private static TariffDao tariffDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        service = RealTariffService.INSTANCE;
        tariffDao = Mockito.mock(TariffDao.class);
    }

    @Test
    public void findAll_receiveAllTariffs_whenExist() {
        List<Tariff> tariffs = createTariffsList();
        Mockito.when(tariffDao.readAll()).thenReturn(Optional.of(tariffs));
        service.setTariffDao(tariffDao);
        List<TariffDto> actual = service.findAll();
        List<TariffDto> expected = new ArrayList<>();
        for (Tariff tariff : tariffs) {
            expected.add(convertToDto(tariff));
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findSpecialOffers_receiveListWithSpecialOffers_whenExist() {
        List<Tariff> tariffs = createTariffsList();
        Mockito.when(tariffDao.readAll()).thenReturn(Optional.of(tariffs));
        service.setTariffDao(tariffDao);
        List<TariffDto> actual = service.findSpecialOffers();
        List<TariffDto> expected = new ArrayList<>();
        expected.add(TariffDto.builder()
                .withName("Second default")
                .withDescription("Second default description")
                .withSpecialOffer(true)
                .withCostPerDay(new BigDecimal("2"))
                .withDownloadSpeed(200.0)
                .withUploadSpeed(150.0)
                .build());
        assertEquals(expected, actual);
    }

    @Test
    public void findByName_receiveTariffWithGivenName_whenExist() {
        Tariff tariff = createTariff();
        Mockito.when(tariffDao.findByName(tariff.getName())).thenReturn(Optional.of(tariff));
        service.setTariffDao(tariffDao);
        Optional<TariffDto> actual = service.findByName(tariff.getName());
        Optional<TariffDto> expected = Optional.of(convertToDto(tariff));
        assertEquals(expected, actual);
    }

    @Test
    public void findTariffId_receiveTariffId_whenExist() {
        Tariff tariff = createTariff();
        Mockito.when(tariffDao.findByName(tariff.getName())).thenReturn(Optional.of(tariff));
        service.setTariffDao(tariffDao);
        Optional<Integer> actual = service.findTariffId(tariff.getName());
        Optional<Integer> expected = Optional.of(tariff.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void save_getOldValue_whenTariffExist() {
        Tariff tariff = createTariff();
        TariffDto tariffDto = createTariffDto();
        Mockito.when(tariffDao.update(tariff)).thenReturn(Optional.of(tariff));
        service.setTariffDao(tariffDao);
        Optional<TariffDto> actual = service.save(tariffDto);
        Optional<TariffDto> expected = Optional.of(convertToDto(tariff));
        assertEquals(expected, actual);
    }

    @Test
    public void save_getOptionalEmpty_whenTariffNotExist() {
        Tariff tariff = createTariff();
        TariffDto tariffDto = createTariffDto();
        Mockito.when(tariffDao.update(tariff)).thenReturn(Optional.empty());
        service.setTariffDao(tariffDao);
        Optional<TariffDto> actual = service.save(tariffDto);
        Optional<TariffDto> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    private Tariff createTariff() {
        return Tariff.builder()
                .withId(1)
                .withName("Default")
                .withDescription("default description")
                .withSpecialOffer(false)
                .withCostPerDay(new BigDecimal("1"))
                .withDownloadSpeed(100.0)
                .withUploadSpeed(50.0)
                .build();
    }

    private TariffDto createTariffDto() {
        return TariffDto.builder()
                .withName("Default")
                .withDescription("default description")
                .withSpecialOffer(false)
                .withCostPerDay(new BigDecimal("1"))
                .withDownloadSpeed(100.0)
                .withUploadSpeed(50.0)
                .build();
    }

    private List<Tariff> createTariffsList() {
        List<Tariff> tariffs = new ArrayList<>();
        Tariff tariff = Tariff.builder()
                .withId(1)
                .withName("Default")
                .withDescription("default description")
                .withSpecialOffer(false)
                .withCostPerDay(new BigDecimal("1"))
                .withDownloadSpeed(100.0)
                .withUploadSpeed(50.0)
                .build();
        Tariff secondTariff = Tariff.builder()
                .withId(2)
                .withName("Second default")
                .withDescription("Second default description")
                .withSpecialOffer(true)
                .withCostPerDay(new BigDecimal("2"))
                .withDownloadSpeed(200.0)
                .withUploadSpeed(150.0)
                .build();
        tariffs.add(tariff);
        tariffs.add(secondTariff);
        return tariffs;
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
}