package com.yurwar.trainingcourse.dto;

import com.yurwar.trainingcourse.model.ActivityImportance;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDTO {
    private String name;
    private String description;
    private ActivityImportance importance;
}
