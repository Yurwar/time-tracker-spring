package com.yurwar.trainingcourse.model.repository;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository that provides access to activity request entity mapping in database
 */
@Repository
public interface ActivityRequestRepository extends JpaRepository<ActivityRequest, Long> {
    List<ActivityRequest> findByActivityIdAndUserId(Long activityId, Long userId);
}
