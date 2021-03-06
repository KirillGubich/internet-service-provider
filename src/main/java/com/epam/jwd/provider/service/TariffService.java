package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.TariffDto;

import java.util.List;

public interface TariffService extends CommonService<TariffDto> {

    List<TariffDto> findSpecialOffers();
}
