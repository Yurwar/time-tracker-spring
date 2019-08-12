package com.yurwar.trainingcourse.model.repository;

import com.yurwar.trainingcourse.model.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository that provide access to activity entity mapping in database
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
