package com.yurwar.trainingcourse.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ActivityDurationDTO {
    //TODO i18n
    @PositiveOrZero(message = "Amount of days must be positive or zero")
    @NotNull(message = "Amount of days must be positive or zero")
    private Integer days;

    @PositiveOrZero(message = "Amount of hours must be positive or zero")
    @NotNull(message = "Amount of hours must be positive or zero")
    private Integer hours;

    @PositiveOrZero(message = "Amount of minutes must be positive or zero")
    @NotNull(message = "Amount of hours must be positive or zero")
    private Integer minutes;
}
