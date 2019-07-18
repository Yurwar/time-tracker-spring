package com.yurwar.trainingcourse.repository;

import com.yurwar.trainingcourse.model.ActivityRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRequestRepository extends JpaRepository<ActivityRequest, Long> {
    boolean existsByActivityIdAndUserId(long activityId, long userId);

    Optional<ActivityRequest> findByActivityIdAndUserId(Long activityId, Long userId);
}
