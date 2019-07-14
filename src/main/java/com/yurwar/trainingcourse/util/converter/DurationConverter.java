package com.yurwar.trainingcourse.util.converter;

import javax.persistence.AttributeConverter;
import java.time.Duration;

public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return duration != null ? duration.toMinutes() : null;
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return dbData != null ? Duration.ofMinutes(dbData) : null;
    }
}
