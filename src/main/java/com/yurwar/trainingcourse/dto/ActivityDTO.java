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
    @NotBlank(message = "{validation.activity.name.not_blank}")
    @Size(min = 5, max = 100, message = "{validation.activity.name.size}")
    private String name;
    @Size(max = 500, message = "{validation.activity.description.size}")
    private String description;
    @NotNull(message = "{validation.activity.importance.not_null}")
    private ActivityImportance importance;
}
