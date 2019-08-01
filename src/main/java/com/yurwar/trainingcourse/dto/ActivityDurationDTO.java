package com.yurwar.trainingcourse.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ActivityDurationDTO {
    @PositiveOrZero(message = "{validation.activity.duration.days.positive_or_zero}")
    @NotNull(message = "{validation.activity.duration.days.not_null}")
    private Integer days;

    @PositiveOrZero(message = "{validation.activity.duration.hours.positive_or_zero}")
    @NotNull(message = "{validation.activity.duration.hours.not_null}")
    private Integer hours;

    @PositiveOrZero(message = "{validation.activity.duration.minutes.positive_or_zero}")
    @NotNull(message = "{validation.activity.duration.minutes.not_null}")
    private Integer minutes;
}
