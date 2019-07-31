package com.yurwar.trainingcourse.dto;

import com.yurwar.trainingcourse.entity.ActivityImportance;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDTO {
    //TODO i18n
    @NotBlank(message = "Activity name must be not blank")
    @Size(min = 5, message = "Minimal size of activity name must be more than 5")
    private String name;
    private String description;
    @NotNull(message = "Activity importance must be not empty")
    private ActivityImportance importance;
}
