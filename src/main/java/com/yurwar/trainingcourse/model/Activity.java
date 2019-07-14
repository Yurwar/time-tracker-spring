package com.yurwar.trainingcourse.model;

import com.yurwar.trainingcourse.util.converter.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users", "activityRequests"})
@ToString(exclude = {"users", "activityRequests"})
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private ActivityImportance importance;

    @Column(name = "start_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean finished;

    @ManyToMany(mappedBy = "activities")
    private Set<User> users;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private Set<ActivityRequest> activityRequests;
}
