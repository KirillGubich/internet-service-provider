package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.entity.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * Expands the basic capabilities of the {@link CommonService} for interacting with {@link Subscription}
 *
 * @author Kirill Gubich
 */
public interface SubscriptionService extends CommonService<SubscriptionDto> {

    /**
     * Finds all user subscriptions
     *
     * @param userId user id
     * @return list with subscriptions
     */
    List<SubscriptionDto> findUserSubscriptions(Integer userId);

    /**
     * Finds subscription by id
     *
     * @param id subscription id
     * @return subscription with given id, if not found - empty optional
     */
    Optional<SubscriptionDto> findById(Integer id);
}
