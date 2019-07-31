package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.ActivityDTO;
import com.yurwar.trainingcourse.dto.ActivityDurationDTO;
import com.yurwar.trainingcourse.entity.Activity;
import com.yurwar.trainingcourse.entity.ActivityImportance;
import com.yurwar.trainingcourse.entity.User;
import com.yurwar.trainingcourse.service.ActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String getAddActivityPage(Model model,
                                     @ModelAttribute("activity") ActivityDTO activityDTO) {
        model.addAttribute("importanceLevels", ActivityImportance.values());
        return "add-activity";
    }

    @PostMapping("/activities/add")
    public String addActivity(@ModelAttribute("activity") @Valid ActivityDTO activityDTO,
                              BindingResult bindingResult,
                              Model model) {
        log.info(activityDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("importanceLevels", ActivityImportance.values());
            return "add-activity";
        }

        activityService.addNewActivity(activityDTO);

        return "redirect:/activities";
    }

    @PostMapping("/activities/delete/{id}")
    public String deleteActivity(@PathVariable("id") long activityId,
                                 Model model) {
        Activity activity = activityService.findActivityById(activityId);
        activityService.deleteActivity(activity);

        return "redirect:/activities";
    }

    @ModelAttribute("duration")
    public ActivityDurationDTO getActivityDurationDTO() {
        return new ActivityDurationDTO();
    }

    @PostMapping("/activities/mark-time/{id}")
    public String markTimeSpent(@AuthenticationPrincipal User user,
                                @PathVariable("id") long activityId,
                                @ModelAttribute("duration") @Valid ActivityDurationDTO durationDTO,
                                BindingResult bindingResult,
                                Model model) {
        log.info(durationDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("activities", activityService.findAllActivities());
            return "activities";
        }

        activityService.markTimeSpent(activityId, user, durationDTO);

        return "redirect:/activities";
    }
}
