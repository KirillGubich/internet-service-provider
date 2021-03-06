package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.SubscriptionDao;
import com.epam.jwd.provider.factory.impl.AddressDtoFactory;
import com.epam.jwd.provider.factory.impl.AddressFactory;
import com.epam.jwd.provider.model.dto.AddressDto;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.entity.Address;
import com.epam.jwd.provider.model.entity.Subscription;
import com.epam.jwd.provider.service.SubscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RealSubscriptionService implements SubscriptionService {
    INSTANCE;

    private final SubscriptionDao subscriptionDao = SubscriptionDao.INSTANCE;
    private static final Integer DEFAULT_ADDRESS_ID = 0;

    @Override
    public List<SubscriptionDto> findAll() {
        Optional<List<Subscription>> subscriptions = subscriptionDao.readAll();
        return subscriptions.map(subscriptionList -> subscriptionList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public void create(SubscriptionDto dto) {
        subscriptionDao.create(convertToSubscription(dto));
    }

    @Override
    public Optional<SubscriptionDto> save(SubscriptionDto dto) {
        Optional<Subscription> oldSubscription = subscriptionDao.update(convertToSubscription(dto));
        return oldSubscription.map(this::convertToDto);
    }

    @Override
    public void delete(SubscriptionDto dto) {
        subscriptionDao.delete(convertToSubscription(dto));
    }

    @Override
    public List<SubscriptionDto> findUserSubscriptions(Integer userId) {
        Optional<List<Subscription>> userSubscriptions = subscriptionDao.findUserSubscriptions(userId);
        return userSubscriptions.map(subscriptionList -> subscriptionList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    private Subscription convertToSubscription(SubscriptionDto dto) {
        return Subscription.builder()
                .withId(dto.getId())
                .withTariffName(dto.getTariffName())
                .withTariffDescription(dto.getTariffDescription())
                .withStartDate(dto.getStartDate())
                .withEndDate(dto.getEndDate())
                .withPrice(dto.getPrice())
                .withStatus(dto.getStatus())
                .withAddress(convertToAddress(dto.getAddress()))
                .build();
    }

    private SubscriptionDto convertToDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .withId(subscription.getId())
                .withTariffName(subscription.getTariffName())
                .withTariffDescription(subscription.getTariffDescription())
                .withStartDate(subscription.getStartDate())
                .withEndDate(subscription.getEndDate())
                .withPrice(subscription.getPrice())
                .withStatus(subscription.getStatus())
                .withAddress(convertToAddressDto(subscription.getAddress()))
                .build();

    }

    private Address convertToAddress(AddressDto dto) {
        return AddressFactory.INSTANCE.create(DEFAULT_ADDRESS_ID, dto.getCity(), dto.getAddress());
    }

    private AddressDto convertToAddressDto(Address address) {
        return AddressDtoFactory.INSTANCE.create(address.getCity(), address.getAddress());
    }
}
