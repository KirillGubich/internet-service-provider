package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.factory.impl.AddressDtoFactory;
import com.epam.jwd.provider.model.dto.AddressDto;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import com.epam.jwd.provider.service.SubscriptionService;
import org.junit.Test;

import java.sql.SQLException;

public class RealSubscriptionServiceTest {

    @Test
    public void delete() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        SubscriptionService service = RealSubscriptionService.INSTANCE;
        AddressDto addressDto = AddressDtoFactory.INSTANCE.create("Minsk", "Surganov 85-13");
        service.delete(SubscriptionDto.builder().withId(2).withAddress(addressDto).build());
        ProviderConnectionPool.getInstance().destroyPool();
    }
}