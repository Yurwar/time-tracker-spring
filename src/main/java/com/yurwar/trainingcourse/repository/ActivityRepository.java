package com.yurwar.trainingcourse.repository;

import com.yurwar.trainingcourse.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
