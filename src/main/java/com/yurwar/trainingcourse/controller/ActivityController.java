package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.ActivityDTO;
import com.yurwar.trainingcourse.model.ActivityImportance;
import com.yurwar.trainingcourse.service.ActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
public class ActivityController {
    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public String getActivitiesPage(Model model) {
        model.addAttribute("activities", activityService.findAllActivities());
        return "activities";
    }

    @GetMapping("/activities/add")
    public String getAddActivityPage(Model model) {
        model.addAttribute("activity", new ActivityDTO());
        model.addAttribute("importanceLevels", ActivityImportance.values());
        return "add-activity";
    }

    @PostMapping("/activities/add")
    public String addActivity(ActivityDTO activityDTO, Model model) {
        log.info(activityDTO);
        activityService.addActivity(activityDTO);
        model.addAttribute("message", "Activity added success");
        model.addAttribute("activity", activityDTO);
        model.addAttribute("importanceLevels", ActivityImportance.values());
        return "add-activity";
    }
}
