package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActivityController {
    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public String getActivitiesPage(Model model) {
        model.addAttribute("activities", activityService.findAll());
        return "activities";
    }
}
