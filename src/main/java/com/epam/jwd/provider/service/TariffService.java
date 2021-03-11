package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.TariffDto;

import java.util.List;
import java.util.Optional;

public interface TariffService extends CommonService<TariffDto> {

    List<TariffDto> findSpecialOffers();

    Optional<TariffDto> findByName(String name);

    Optional<Integer> findTariffId(String name);
}
