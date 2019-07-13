package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.ActivityDTO;
import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    public List<Activity> findAllActivities() {
        return activityRepository.findAll();
    }

    public void addActivity(ActivityDTO activityDTO) {
        activityRepository.save(Activity.builder()
                .name(activityDTO.getName())
                .importance(activityDTO.getImportance())
                .finished(false)
                .build());
    }
}
