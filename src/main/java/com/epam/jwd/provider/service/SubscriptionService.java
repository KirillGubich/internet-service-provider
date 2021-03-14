package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.SubscriptionDto;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService extends CommonService<SubscriptionDto> {

    List<SubscriptionDto> findUserSubscriptions(Integer userId);

    Optional<SubscriptionDto> findById(Integer id);
}
