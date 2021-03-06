package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService extends CommonService<SubscriptionDto> {

    List<SubscriptionDto> findUserSubscriptions(Integer userId);
}
