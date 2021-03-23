package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.model.entity.Tariff;

import java.util.List;
import java.util.Optional;

/**
 * Expands the basic capabilities of the {@link CommonService} for interacting with {@link Tariff}
 *
 * @author Kirill Gubich
 */
public interface TariffService extends CommonService<TariffDto> {

    /**
     * Finds all tariffs, that are special offers
     *
     * @return list with tariffs
     */
    List<TariffDto> findSpecialOffers();

    /**
     * Finds tariff by name
     *
     * @param name tariff name
     * @return tariff with given name if exist, else - empty optional
     */
    Optional<TariffDto> findByName(String name);

    /**
     * Finds tariff id by its name
     *
     * @param name tariff name
     * @return tairff id if exist, else - empty optional
     */
    Optional<Integer> findTariffId(String name);
}
