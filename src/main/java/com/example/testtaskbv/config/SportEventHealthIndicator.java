package com.example.testtaskbv.config;

import com.example.testtaskbv.repository.SportEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class SportEventHealthIndicator implements HealthIndicator {

    private final SportEventRepository sportEventRepository;

    @Autowired
    public SportEventHealthIndicator(SportEventRepository sportEventRepository) {
        this.sportEventRepository = sportEventRepository;
    }

    @Override
    public Health health() {
        try {
            long unsettledEvents = sportEventRepository.countBySettledFalse();
            if (unsettledEvents > 0) {
                return Health.up()
                        .withDetail("unsettledEvents", unsettledEvents)
                        .withDetail("message", "Active sport events available")
                        .build();
            } else {
                return Health.down()
                        .withDetail("unsettledEvents", 0)
                        .withDetail("message", "No active sport events available")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withException(e)
                    .withDetail("message", "Error checking sport events")
                    .build();
        }
    }
}