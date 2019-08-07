package com.yurwar.trainingcourse.model.entity;

import com.yurwar.trainingcourse.util.converter.DurationConverter;
import com.yurwar.trainingcourse.util.converter.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"activityRequests"})
@ToString(exclude = {"activityRequests"})
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @SequenceGenerator(name = "activityIdSeq", sequenceName = "activities_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityIdSeq")
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private ActivityImportance importance;

    @Enumerated(value = EnumType.STRING)
    private ActivityStatus status;

    @Column(name = "start_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime endTime;

    @Column(name = "duration")
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "activities", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<User> users;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private Set<ActivityRequest> activityRequests;
}
