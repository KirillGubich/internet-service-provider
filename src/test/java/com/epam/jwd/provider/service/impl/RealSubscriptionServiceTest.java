package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.factory.impl.AddressDtoFactory;
import com.epam.jwd.provider.model.dto.AddressDto;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import com.epam.jwd.provider.service.SubscriptionService;
import org.junit.Test;

import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class RealSubscriptionServiceTest {

    @Test
    public void delete() throws SQLException {
//        ProviderConnectionPool.getInstance().init();
//        SubscriptionService service = RealSubscriptionService.INSTANCE;
//        AddressDto addressDto = AddressDtoFactory.INSTANCE.create("Minsk", "Surganov 85-13");
//        service.delete(SubscriptionDto.builder().withId(2).withAddress(addressDto).build());
//        ProviderConnectionPool.getInstance().destroyPool();

        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(new Locale("es"));
        //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en"));
        String formattedDate = formatter.format(currentDate);
        System.out.println(formattedDate);
    }
}