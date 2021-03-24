package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.AddressDao;
import com.epam.jwd.provider.dao.impl.SubscriptionDao;
import com.epam.jwd.provider.exception.AddressExistenceException;
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

    private SubscriptionDao subscriptionDao = SubscriptionDao.getInstance();
    private AddressDao addressDao = AddressDao.getInstance();
    private static final Integer DEFAULT_ADDRESS_ID = 0;

    public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

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
        addressDao.create(convertToAddress(dto.getAddress(), DEFAULT_ADDRESS_ID));
        Optional<Integer> addressId = addressDao.findAddressId(dto.getAddress().getCity(),
                dto.getAddress().getAddress());
        if (!addressId.isPresent()) {
            throw new AddressExistenceException("The newly created address was not found");
        }
        subscriptionDao.create(convertToSubscription(dto, addressId.get()));
    }

    @Override
    public Optional<SubscriptionDto> save(SubscriptionDto dto) {
        Optional<Integer> addressId = addressDao.findAddressId(dto.getAddress().getCity(),
                dto.getAddress().getAddress());
        if (!addressId.isPresent()) {
            throw new AddressExistenceException("The newly created address was not found");
        }
        Optional<Subscription> oldSubscription = subscriptionDao.update(convertToSubscription(dto, addressId.get()));
        return oldSubscription.map(this::convertToDto);
    }

    @Override
    public void delete(SubscriptionDto dto) {
        Optional<Integer> addressId = addressDao.findAddressId(dto.getAddress().getCity(),
                dto.getAddress().getAddress());
        if (!addressId.isPresent()) {
            throw new AddressExistenceException("Subscription exist without address");
        }
        addressDao.delete(convertToAddress(dto.getAddress(), addressId.get()));
        subscriptionDao.delete(convertToSubscription(dto, DEFAULT_ADDRESS_ID));
    }

    @Override
    public List<SubscriptionDto> findUserSubscriptions(Integer userId) {
        Optional<List<Subscription>> userSubscriptions = subscriptionDao.findUserSubscriptions(userId);
        return userSubscriptions.map(subscriptionList -> subscriptionList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public Optional<SubscriptionDto> findById(Integer id) {
        Optional<Subscription> subscription = subscriptionDao.findById(
                Subscription.builder()
                        .withId(id)
                        .build());
        return subscription.map(this::convertToDto);
    }


    private Subscription convertToSubscription(SubscriptionDto dto, Integer addressId) {
        return Subscription.builder()
                .withId(dto.getId())
                .withUserId(dto.getUserId())
                .withTariffId(dto.getTariffId())
                .withTariffName(dto.getTariffName())
                .withTariffDescription(dto.getTariffDescription())
                .withStartDate(dto.getStartDate())
                .withEndDate(dto.getEndDate())
                .withPrice(dto.getPrice())
                .withStatus(dto.getStatus())
                .withAddress(convertToAddress(dto.getAddress(), addressId))
                .build();
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

    private Address convertToAddress(AddressDto dto, Integer id) {
        return AddressFactory.INSTANCE.create(id, dto.getCity(), dto.getAddress());
    }

    private AddressDto convertToAddressDto(Address address) {
        return AddressDtoFactory.INSTANCE.create(address.getCity(), address.getAddress());
    }
}
