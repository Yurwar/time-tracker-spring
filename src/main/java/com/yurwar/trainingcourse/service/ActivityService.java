package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.model.ActivityImportance;
import com.yurwar.trainingcourse.repository.ActivityRepository;
import com.yurwar.trainingcourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public void save() {
        activityRepository.save(Activity.builder()
                .name("Test activity #1")
                .importance(ActivityImportance.EXTREMELY_HIGH)
                .startTime(LocalDateTime.now())
                .finished(false)
                .build());
        activityRepository.save(Activity.builder()
                .name("Test activity #2")
                .importance(ActivityImportance.EXTREMELY_HIGH)
                .startTime(LocalDateTime.now())
                .finished(false)
                .build());
        activityRepository.save(Activity.builder()
                .name("Test activity #3")
                .importance(ActivityImportance.EXTREMELY_HIGH)
                .startTime(LocalDateTime.now())
                .finished(false)
                .build());
        activityRepository.save(Activity.builder()
                .name("Test activity #4")
                .importance(ActivityImportance.EXTREMELY_HIGH)
                .startTime(LocalDateTime.now())
                .finished(false)
                .build());
    }
}
