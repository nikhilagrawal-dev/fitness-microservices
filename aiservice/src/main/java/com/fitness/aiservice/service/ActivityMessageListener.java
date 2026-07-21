package com.fitness.aiservice.service;

import com.fitness.aiservice.RecommendationRepository;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository recommendationRepository;

    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "activity-processor-group"
    )
    public void processActivity(Activity activity) {

        try {

            log.info("========== MESSAGE RECEIVED ==========");
            log.info("Activity: {}", activity);

            Recommendation recommendation =
                    activityAIService.generateRecommendation(activity);

            recommendationRepository.save(recommendation);

            log.info("Recommendation saved successfully.");

        } catch (Exception e) {

            log.error("Error while processing activity", e);

        }
    }
}