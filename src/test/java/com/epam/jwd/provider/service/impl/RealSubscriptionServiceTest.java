package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.AddressDao;
import com.epam.jwd.provider.dao.impl.SubscriptionDao;
import com.epam.jwd.provider.exception.AddressExistenceException;
import com.epam.jwd.provider.model.dto.AddressDto;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.entity.Address;
import com.epam.jwd.provider.model.entity.Subscription;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RealSubscriptionServiceTest {

    private static RealSubscriptionService service;
    private static SubscriptionDao subscriptionDao;
    private static AddressDao addressDao;

    @BeforeClass
    public static void beforeClass() {
        service = RealSubscriptionService.INSTANCE;
        subscriptionDao = Mockito.mock(SubscriptionDao.class);
        addressDao = Mockito.mock(AddressDao.class);
    }

    @Test
    public void findAll_receiveAllSubscriptions_whenExist() {
        List<Subscription> subscriptions = createSubscriptionsList();
        List<SubscriptionDto> expected = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            expected.add(convertToDto(subscription));
        }
        Mockito.when(subscriptionDao.readAll()).thenReturn(Optional.of(subscriptions));
        service.setSubscriptionDao(subscriptionDao);
        List<SubscriptionDto> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test(expected = AddressExistenceException.class)
    public void create_catchAddressExistenceException_whenNewlyCreatedAddressWasNotFound() {
        SubscriptionDto dto = receiveSubscriptionDto();
        Mockito.when(addressDao.findAddressId(dto.getAddress().getCity(), dto.getAddress().getAddress()))
                .thenReturn(Optional.empty());
        service.setAddressDao(addressDao);
        service.create(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_receiveException_whenParamIsNull() {
        service.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_receiveException_whenParamIsNull() {
        service.delete(null);
    }

    @Test(expected = AddressExistenceException.class)
    public void save_catchAddressExistenceException_whenNewlyCreatedAddressWasNotFound() {
        SubscriptionDto dto = receiveSubscriptionDto();
        Mockito.when(addressDao.findAddressId(dto.getAddress().getCity(), dto.getAddress().getAddress()))
                .thenReturn(Optional.empty());
        service.setAddressDao(addressDao);
        service.save(dto);
    }

    @Test
    public void save_getOldValue_whenSaveSubscription() {
        SubscriptionDto dto = receiveSubscriptionDto();
        Mockito.when(addressDao.findAddressId(dto.getAddress().getCity(), dto.getAddress().getAddress()))
                .thenReturn(Optional.of(1));
        Subscription subscription = receiveSubscription();
        Mockito.when(subscriptionDao.update(subscription)).thenReturn(Optional.of(subscription));
        service.setAddressDao(addressDao);
        service.setSubscriptionDao(subscriptionDao);
        Optional<SubscriptionDto> actual = service.save(dto);
        Optional<SubscriptionDto> expected = Optional.of(dto);
        assertEquals(expected, actual);
    }

    @Test(expected = AddressExistenceException.class)
    public void delete_catchAddressExistenceException_whenNewlyCreatedAddressWasNotFound() {
        SubscriptionDto dto = receiveSubscriptionDto();
        Mockito.when(addressDao.findAddressId(dto.getAddress().getCity(), dto.getAddress().getAddress()))
                .thenReturn(Optional.empty());
        service.setAddressDao(addressDao);
        service.delete(dto);
    }

    @Test
    public void findUserSubscriptions_receiveAllUserSubscriptions_whenExist() {
        List<Subscription> subscriptions = createSubscriptionsList();
        final Integer userId = 1;
        Mockito.when(subscriptionDao.findUserSubscriptions(userId)).thenReturn(Optional.of(subscriptions));
        service.setSubscriptionDao(subscriptionDao);
        List<SubscriptionDto> actual = service.findUserSubscriptions(userId);
        List<SubscriptionDto> expected = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            expected.add(convertToDto(subscription));
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findById_receiveSubscription_whenSubscriptionIsPresent() {
        Subscription subscription = receiveSubscription();
        Integer id = 1;
        Mockito.when(subscriptionDao.findById(Subscription.builder().withId(id).build()))
                .thenReturn(Optional.of(subscription));
        service.setSubscriptionDao(subscriptionDao);
        Optional<SubscriptionDto> actual = service.findById(id);
        Optional<SubscriptionDto> expected = Optional.of(convertToDto(subscription));
        assertEquals(expected, actual);
    }

    private SubscriptionDto receiveSubscriptionDto() {
        return SubscriptionDto.builder()
                .withId(1)
                .withUserId(1)
                .withTariffId(1)
                .withStartDate(LocalDate.of(2021, 3, 20))
                .withEndDate(LocalDate.of(2021, 3, 30))
                .withPrice(new BigDecimal("100"))
                .withTariffName("Base tariff")
                .withTariffDescription("any description")
                .withAddress(new AddressDto("Minsk", "Victory 1"))
                .withStatus(SubscriptionStatus.REQUESTED)
                .build();
    }

    private Subscription receiveSubscription() {
        return Subscription.builder()
                .withId(1)
                .withUserId(1)
                .withTariffId(1)
                .withStartDate(LocalDate.of(2021, 3, 20))
                .withEndDate(LocalDate.of(2021, 3, 30))
                .withPrice(new BigDecimal("100"))
                .withTariffName("Base tariff")
                .withTariffDescription("any description")
                .withAddress(new Address(1, "Minsk", "Victory 1"))
                .withStatus(SubscriptionStatus.REQUESTED)
                .build();
    }

    private List<Subscription> createSubscriptionsList() {
        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription = Subscription.builder()
                .withId(1)
                .withUserId(1)
                .withTariffId(1)
                .withStartDate(LocalDate.of(2021, 3, 20))
                .withEndDate(LocalDate.of(2021, 3, 30))
                .withPrice(new BigDecimal("100"))
                .withTariffName("Base tariff")
                .withTariffDescription("any description")
                .withAddress(new Address(1, "Minsk", "Victory 1"))
                .withStatus(SubscriptionStatus.REQUESTED)
                .build();
        Subscription secondSubscription = Subscription.builder()
                .withId(2)
                .withUserId(1)
                .withTariffId(2)
                .withStartDate(LocalDate.of(2021, 3, 20))
                .withEndDate(LocalDate.of(2021, 4, 30))
                .withPrice(new BigDecimal("200"))
                .withTariffName("Base tariff")
                .withTariffDescription("Base description")
                .withAddress(new Address(1, "Mogilev", "Central 1"))
                .withStatus(SubscriptionStatus.REQUESTED)
                .build();
        subscriptions.add(subscription);
        subscriptions.add(secondSubscription);
        return subscriptions;
    }

    private SubscriptionDto convertToDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .withId(subscription.getId())
                .withUserId(subscription.getUserId())
                .withTariffId(subscription.getTariffId())
                .withTariffName(subscription.getTariffName())
                .withTariffDescription(subscription.getTariffDescription())
                .withStartDate(subscription.getStartDate())
                .withEndDate(subscription.getEndDate())
                .withPrice(subscription.getPrice())
                .withStatus(subscription.getStatus())
                .withAddress(convertToAddressDto(subscription.getAddress()))
                .build();
    }

    private AddressDto convertToAddressDto(Address address) {
        return new AddressDto(address.getCity(), address.getAddress());
    }
}