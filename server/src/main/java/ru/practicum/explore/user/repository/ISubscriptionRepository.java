package ru.practicum.explore.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.user.model.Subscription;
import ru.practicum.explore.user.model.SubscriptionKey;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionRepository extends JpaRepository<Subscription, SubscriptionKey> {

    Optional<Subscription> findSubscriptionById_UserIdAndId_SubscriberId(Integer userId, Integer subscrId);

    List<Subscription> findAllById_UserIdOrderByCreatedOnDesc(Integer userId);

    List<Subscription> findAllById_SubscriberIdOrderByCreatedOnDesc(Integer subsrId);

    void deleteAllById_UserId(Integer userId);
}
